package com.wioyber.kele.core.config;


import com.alibaba.fastjson.JSONObject;
import com.wioyber.kele.core.common.sys.Result;
import com.wioyber.kele.core.enums.exception.CustomExceptionEnum;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class CustomShiroFilter extends AuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		Subject subject = getSubject(request, response);
		String header = httpRequest.getHeader("X-Requested-With");
		//前后端分离项目 返回json
		response.setContentType("application/json; charset=utf-8");
		if (subject.getPrincipal() == null) {
			if ("XMLHttpRequest".equalsIgnoreCase(header)) {
				response.getWriter().write(
						JSONObject.toJSONString(
								Result.error(CustomExceptionEnum.SYS_LOGIN_FAIL)));
				return false;
			}
			saveRequestAndRedirectToLogin(request, response);
		} else {
			if ("XMLHttpRequest".equalsIgnoreCase(header)) {
				response.getWriter().write(JSONObject.toJSONString(Result.error(CustomExceptionEnum.SYS_NO_AUTHOR)));
				return false;
			}
			saveRequestAndRedirectToLogin(request, response);
		}
		return false;
	}

	/**
	 * 重写重定向方法，不再重定向
	 * */
	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().write(JSONObject.toJSONString(Result.error(CustomExceptionEnum.SYS_LOGIN_FAIL)));
	}
}