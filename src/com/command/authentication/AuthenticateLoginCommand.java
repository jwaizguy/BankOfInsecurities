package com.command.authentication;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.command.Command;
import com.constants.ControllerConstants;
import com.dao.SessionDataDAO;
import com.orm.SessionData;

/** Cookie checks to be sure the user is who they say they are.  Redirects to login page if not. **/
public class AuthenticateLoginCommand implements Command {
	private String userName;
	private String cookieValue;
	private String sessionIDCookieValue;
	private ModelAndView modelAndView;
	private HttpServletResponse response;
	private boolean isSuccessful = false;
	private static Logger logger = Logger.getLogger(AuthenticateLoginCommand.class);
	private SessionDataDAO sessionDataDAO;
	
	/* Assumes to not auto-execute */
	public AuthenticateLoginCommand(String cookieValue, String userName, ModelAndView modelAndView, String sessionIDCookieValue, SessionDataDAO sessionDataDAO) {
		this.userName = userName;
		this.cookieValue = cookieValue;
		this.sessionIDCookieValue = sessionIDCookieValue;
		this.modelAndView = modelAndView;
		this.sessionDataDAO = sessionDataDAO;
	}
	
	@Override
	public void execute() {
		SessionData sessionData = new SessionData(this.sessionIDCookieValue, this.userName);
		
		if(this.cookieValue.equalsIgnoreCase(ControllerConstants.NULL_COOKIE) || 
				! this.cookieValue.equals(this.userName) ||
				! this.sessionDataDAO.isValidCredentials(sessionData)) {
			this.modelAndView = new ModelAndView("redirect:" + ControllerConstants.LOGIN_PAGE_DIRECTORY);
			this.isSuccessful = false;
			logger.info("bouncing!");
		} else {
			logger.info("allowing to pass");
			this.isSuccessful = true;
			//UtilityFunctions.addAuthenticatedCookies(this.response, this.userName);
		}
	}
	
	public boolean isSuccessful() {
		return this.isSuccessful;
	}
	
	public ModelAndView getModelAndView() {
		return this.modelAndView;
	}
	
	public HttpServletResponse getHttpServletResponse() {
		return this.response;
	}

	@Override
	public void undo() { }
}
