package com.wioyber.kele.core.config;


import com.wioyber.kele.core.common.sys.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisSessionDao extends AbstractSessionDAO {

    @Resource(name = "customRedisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;

    @Value("${shiro.session.expire}")
    private long expire;

    @Override
    public void update(Session session) throws UnknownSessionException {
        log.debug("更新seesion,roleId=[{}]", session.getId().toString());
        try {
            redisTemplate.opsForValue().set(getKey(session.getId().toString()), session, expire, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getKey(String originalKey) {
        return SystemConstant.USER_INFO_TOKEN_PREFIX + originalKey;
    }

    @Override
    public void delete(Session session) {
        log.debug("删除seesion,roleId=[{}]", session.getId().toString());
        try {
            String key = getKey(session.getId().toString());
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Override
    public Collection<Session> getActiveSessions() {
        log.info("获取存活的session");
        return Collections.emptySet();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        log.debug("创建seesion,roleId=[{}]", session.getId().toString());
        try {
            String key = getKey(session.getId().toString());
            redisTemplate.opsForValue().set(key, session, expire, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return sessionId;
    }

    @Override
    public Session readSession(Serializable sessionId) {
        try {
            return super.readSession(sessionId);
        } catch (UnknownSessionException ex) {
            log.warn("There is no session with roleId [" + sessionId + "]");
        }
        return null;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.debug("获取seesion,roleId=[{}]", sessionId.toString());
        Session readSession = null;
        try {
            readSession = (Session) redisTemplate.opsForValue().get(getKey(sessionId.toString()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return readSession;
    }
}