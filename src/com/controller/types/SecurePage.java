package com.controller.types;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface SecurePage {
	public boolean authenticateLogin(String userName, String cookieValue, ModelAndView modelAndView, HttpServletResponse response, String sessionIDValue);
}
