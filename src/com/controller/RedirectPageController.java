package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.command.WipeCookiesCommand;
import com.constants.ControllerConstants;
import com.dao.UserDetailDAO;

@Controller
@RequestMapping(value = ControllerConstants.REDIRECT_PAGE_DIRECTORY)
public class RedirectPageController {
	private final String tableLabel = "contactInformation";
	private final String userNameParameter = "userName";
	private final int numTableCols = 4; 
	private static Logger logger = Logger.getLogger(RedirectPageController.class);
	
	@Autowired
	private UserDetailDAO userDetailDAO;
	
	@RequestMapping(value = "/{redirectTo}", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("redirectTo") String redirectTo, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.REDIRECT_PAGE_NAME);
		
		if(redirectTo.equalsIgnoreCase(ControllerConstants.LOGIN_PAGE_NAME)) {
			this.handleLogout(request, response);
		}
		
		modelAndView.addObject("redirectTo","/" + redirectTo);
				
		return modelAndView;
	}
	
	private void handleLogout(HttpServletRequest request, HttpServletResponse response) {
		WipeCookiesCommand wipeCookiesCommand = new WipeCookiesCommand(response, request);
		wipeCookiesCommand.execute();
		
		request.getSession(false).invalidate();
	}
}