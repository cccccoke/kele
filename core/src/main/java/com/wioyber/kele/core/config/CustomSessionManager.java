package com.wioyber.kele.core.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.DelegatingSession;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @author cjg
 * @since 2023/3/30
 */

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class CustomSessionManager extends DefaultSessionManager {

    private Cookie sessionIdCookie;

    private boolean sessionIdCookieEnabled;

    public CustomSessionManager() {
        Cookie cookie = new SimpleCookie("tk");
        cookie.setHttpOnly(true);
        sessionIdCookie = cookie;
        sessionIdCookieEnabled = true;
    }

    @Override
    protected void onStart(Session session, SessionContext context) {
        super.onStart(session, context);
        if (!WebUtils.isHttp(context)) {
            log.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response pair. No session ID cookie will be set.");
            return;
        }
        HttpServletRequest request = WebUtils.getHttpRequest(context);
        HttpServletResponse response = WebUtils.getHttpResponse(context);
        if (isSessionIdCookieEnabled()) {
            Serializable sessionId = session.getId();
            storeSessionId(sessionId, request, response);
        } else {
            log.debug("Session ID cookie is disabled.  No cookie has been set for new session with roleId {}", session.getId());
        }
        request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
    }

    public boolean isSessionIdCookieEnabled() {
        return sessionIdCookieEnabled;
    }


    private void storeSessionId(Serializable currentId, HttpServletRequest request, HttpServletResponse response) {
        if (currentId == null) {
            String msg = "sessionId cannot be null when persisting for subsequent requests.";
            throw new IllegalArgumentException(msg);
        } else {
            Cookie template = getSessionIdCookie();
            Cookie cookie = new SimpleCookie(template);
            String idString = currentId.toString();
            cookie.setValue(idString);
            cookie.saveTo(request, response);
            log.trace("Set session ID cookie for session with roleId {}", idString);
        }
    }



    @Override
    protected Session createExposedSession(Session session, SessionContext context) {
        if (!WebUtils.isWeb(context)) {
            return super.createExposedSession(session, context);
        } else {
            ServletRequest request = WebUtils.getRequest(context);
            ServletResponse response = WebUtils.getResponse(context);
            SessionKey key = new WebSessionKey(session.getId(), request, response);
            return new DelegatingSession(this, key);
        }
    }

    @Override
    protected Session createExposedSession(Session session, SessionKey key) {
        if (!WebUtils.isWeb(key)) {
            return super.createExposedSession(session, key);
        } else {
            ServletRequest request = WebUtils.getRequest(key);
            ServletResponse response = WebUtils.getResponse(key);
            SessionKey sessionKey = new WebSessionKey(session.getId(), request, response);
            return new DelegatingSession(this, sessionKey);
        }
    }

    @Override
    protected void onStop(Session session, SessionKey key) {
        super.onStop(session, key);
        if (WebUtils.isHttp(key)) {
            HttpServletRequest request = WebUtils.getHttpRequest(key);
            HttpServletResponse response = WebUtils.getHttpResponse(key);
            log.debug("Session has been stopped (subject logout or explicit stop).  Removing session ID cookie.");
            removeSessionIdCookie(request, response);
        } else {
            log.debug("SessionKey argument is not HTTP compatible or does not have an HTTP request/response pair. Session ID cookie will not be removed due to stopped session.");
        }
    }

    @Override
    protected void onExpiration(Session s, ExpiredSessionException ese, SessionKey key) {
        super.onExpiration(s, ese, key);
        onInvalidation(key);
    }

    @Override
    protected void onInvalidation(Session session, InvalidSessionException ise, SessionKey key) {
        super.onInvalidation(session, ise, key);
        onInvalidation(key);
    }

    private void onInvalidation(SessionKey key) {
        ServletRequest request = WebUtils.getRequest(key);
        if (request != null)
            request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID);
        if (WebUtils.isHttp(key)) {
            log.debug("Referenced session was invalid.  Removing session ID cookie.");
            removeSessionIdCookie(WebUtils.getHttpRequest(key), WebUtils.getHttpResponse(key));
        } else {
            log.debug("SessionKey argument is not HTTP compatible or does not have an HTTP request/response pair. Session ID cookie will not be removed due to invalidated session.");
        }
    }

    private void removeSessionIdCookie(HttpServletRequest request, HttpServletResponse response) {
        getSessionIdCookie().removeFrom(request, response);
    }

    /**
     * 获取session 优化单次请求需要多次访问redis的问题
     *
     * @param sessionKey
     * @return
     * @throws UnknownSessionException
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = this.getSessionId(sessionKey);
        if (sessionId == null) {
            log.debug("Unable to resolve session ID from SessionKey [{}].  " +
                    "Returning null to indicate a session could not be found.", sessionKey);
            return null;
        } else {
            Session s = this.retrieveSessionFromDataSource(sessionId);
            if (s == null) {
                String msg = "Could not find session with ID [" + sessionId + "]";
                throw new UnknownSessionException(msg);
            } else {
                return s;
            }
        }
    }

    @Override
    public Serializable getSessionId(SessionKey key) {
        Serializable id = super.getSessionId(key);
        if (id == null && WebUtils.isWeb(key)) {
            ServletRequest request = WebUtils.getRequest(key);
            ServletResponse response = WebUtils.getResponse(key);
            id = getSessionId(request, response);
        }
        return id;
    }

    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        return getReferencedSessionId(request, response);
    }

    private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {
        String id = getSessionIdCookieValue(request, response);
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "cookie");
        } else {
//            roleId = getUriPathSegmentParamValue(request, "tk");
            if (request instanceof HttpServletRequest) {
                HttpServletRequest req = (HttpServletRequest) request;
                id = req.getHeader("tk");
            }
            if (id == null) {
                String name = getSessionIdName();
                id = request.getParameter(name);
                if (id == null)
                    id = request.getParameter(name.toLowerCase());
            }
            if (id != null)
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "url");
        }
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        }
        return id;
    }

    private String getSessionIdCookieValue(ServletRequest request, ServletResponse response) {
        if (!isSessionIdCookieEnabled()) {
            log.debug("Session ID cookie is disabled - session roleId will not be acquired from a request cookie.");
            return null;
        }
        if (!(request instanceof HttpServletRequest)) {
            log.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
            return null;
        } else {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String sessionId = "";
            //兼容前端直接携带token，并且优先级高于cookie中的token sunx@2019/11/27
            sessionId = httpRequest.getHeader("tk");
            if (null == sessionId || "".equals(sessionId.trim())) {
                sessionId = getSessionIdCookie().readValue(httpRequest, WebUtils.toHttp(response));
            }
            return sessionId;

        }
    }

    private String getSessionIdName() {
        String name = sessionIdCookie == null ? null : sessionIdCookie.getName();
        if (name == null)
            name = "tk";
        return name;
    }

}

