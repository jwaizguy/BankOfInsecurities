package com.command;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.ControllerConstants;

public class WipeCookiesCommand implements PermanentCommand {
	private HttpServletResponse response;
	private HttpServletRequest request;
	
	public WipeCookiesCommand(HttpServletResponse response, HttpServletRequest request) {
		this.response = response;
		this.request = request;
	}

	@Override
	public void execute() {
		Cookie[] cookies = this.request.getCookies();
		
		if(cookies != null) {
			for(int i = 0; i < cookies.length ; i++) {
				cookies[i].setMaxAge(0);
				cookies[i].setPath(ControllerConstants.HOME_DIRECTORY);
				cookies[i].setValue("");
				response.addCookie(cookies[i]);
			}
		}
	}

}
