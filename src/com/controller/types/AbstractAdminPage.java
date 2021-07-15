package com.controller.types;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.command.authentication.AuthenticateAdminCommand;
import com.command.authentication.AuthenticateLoginCommand;
import com.constants.ControllerConstants;
import com.dao.SessionDataDAO;
import com.dao.UserDetailDAO;

public class AbstractAdminPage implements SecurePage {
	
	@Autowired
	protected SessionDataDAO sessionDataDAO;
	@Autowired
	protected UserDetailDAO userDetailDAO;

	private static Logger logger = Logger.getLogger(AbstractAdminPage.class);
	@Override
	public boolean authenticateLogin(String userName, String cookieValue, ModelAndView modelAndView,
			HttpServletResponse response, String sessionIDValue) {
		
		AuthenticateLoginCommand authenticationCommand = new AuthenticateLoginCommand(cookieValue, userName, modelAndView, sessionIDValue, this.sessionDataDAO);
		authenticationCommand.execute();
		
		AuthenticateAdminCommand adminAuthentication = new AuthenticateAdminCommand(this.userDetailDAO, userName);
		adminAuthentication.execute();
		
		boolean output = authenticationCommand.isSuccessful() && adminAuthentication.isSuccessful();
		
		if(! authenticationCommand.isSuccessful()) {
			modelAndView.setViewName("redirect:" + ControllerConstants.LOGIN_PAGE_DIRECTORY);
		}
		
		return output;
	}

}
