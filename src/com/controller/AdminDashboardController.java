package com.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.builders.AdminNavLinkBuilder;
import com.constants.ControllerConstants;
import com.controller.models.PasswordChange;
import com.controller.types.AbstractAdminPage;
import com.dao.UserCredentialDAO;
import com.dao.UserDetailDAO;
import com.orm.UserCredential;

@Controller
@RequestMapping(ControllerConstants.ADMIN_DASHBOARD_DIRECTORY)
public class AdminDashboardController extends AbstractAdminPage {
	
	private static Logger logger = Logger.getLogger(AdminDashboardController.class);
	private AdminNavLinkBuilder adminNavLinkBuilder;
	private final String userDetailsText = "userDetails";
	@Autowired
	@Qualifier("funMoneyTransferring")
	private Boolean isFunTransferring;
	
	@Autowired
	private UserDetailDAO userDetailDao;
	
	@Autowired
	private UserCredentialDAO userCredentialDAO;
	
	public AdminDashboardController() {
		this.adminNavLinkBuilder = new AdminNavLinkBuilder();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{userName}")
	public ModelAndView getHandler(
			@PathVariable("userName") String userName,
			HttpServletResponse response,
			@RequestParam(value="funTransferring", required = false) Boolean isFunTransferringParam,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String usernameCookieValue, 
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIDValue) {
		
		this.adminNavLinkBuilder.init(userName, ControllerConstants.ADMIN_DASHBOARD_DIRECTORY);
		
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.ADMIN_DASHBOARD_PAGE_NAME);
		
		List<UserCredential> userDetails = this.filterResults(userCredentialDAO.getUserCredentialList());
		
		modelAndView.addObject(this.userDetailsText, userDetails);
		
		if(this.authenticateLogin(userName, usernameCookieValue, modelAndView, response, sessionIDValue)) {
			modelAndView.addObject("navLinks", this.adminNavLinkBuilder.getLinks());
		}
		this.toggleFunTransferring(isFunTransferringParam);
		logger.info("Is money transferring enabled? " + this.isFunTransferring);
		
		return modelAndView;
	}
	
	private List<UserCredential> filterResults(List<UserCredential> userCredentials) {
		List<UserCredential> output = new LinkedList<UserCredential>(); 
		
		for(UserCredential user : userCredentials) {
			if(! this.userDetailDAO.isAdmin(user.getId())) {
				output.add(user);
			}
		}
		
		return output;
	}
	
	// delete a given message
	@RequestMapping(method = RequestMethod.POST, value="/{userName}")
	public ModelAndView editCredential(
			@PathVariable("userName") String userName, 
			@ModelAttribute("passwordChange") @Valid PasswordChange passwordChange) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:" + ControllerConstants.ADMIN_DASHBOARD_DIRECTORY + "/" + userName);
		this.changePassword(passwordChange);
		
		return modelAndView;
	}
	
	private void changePassword(PasswordChange passwordChange) {
		// update if the password are different
		String previousPassword = this.userCredentialDAO.getPassword(passwordChange.getUsername());
		if(! previousPassword.equals(passwordChange.getPassword())) {
			logger.info("CHANGING PASSWORD OF " + passwordChange.getUsername() + " FROM: " + previousPassword + " TO: " + passwordChange);
			this.userCredentialDAO.updatePassword(passwordChange.getUsername(), passwordChange.getPassword());
		}
	}
	
	private void toggleFunTransferring(Boolean isFunTransferringParam) {
		if(isFunTransferringParam == null) {
			return;
		}
		
		logger.info(isFunTransferringParam);
		this.isFunTransferring = isFunTransferringParam;
	}
}