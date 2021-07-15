package com.controller.types;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.command.authentication.AuthenticateLoginCommand;
import com.dao.SessionDataDAO;
import com.dao.UserDetailDAO;

public abstract class AbstractController implements SecurePage {
	
	@Autowired
	protected SessionDataDAO sessionDataDAO;
	@Autowired
	protected UserDetailDAO userDetailDAO;
	
	@Override
	public boolean authenticateLogin(String userName, String cookieValue, ModelAndView modelAndView, HttpServletResponse response, String sessionIdValue) {
		AuthenticateLoginCommand authenticationCommand = new AuthenticateLoginCommand(cookieValue, userName, modelAndView, sessionIdValue, sessionDataDAO);
		authenticationCommand.execute();
		boolean output = authenticationCommand.isSuccessful();
		
		if(authenticationCommand.isSuccessful()) {
			//UtilityFunctions.addAuthenticatedCookies(response, userName);
		} else{
			modelAndView.setViewName(authenticationCommand.getModelAndView().getViewName());
		}
		
		return output;
	}
	
	protected void fillDefaultFields(ModelAndView modelAndView, String userName) {
		modelAndView.addObject("userInfo", userName);
		modelAndView.addObject("logoutLink", "/redirect/login");
		modelAndView.addObject("isAdmin", this.userDetailDAO.isAdmin(userName));
	}
}
