package com.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.command.HandleToolbarClickCommand;
import com.command.PlaceNameInViewCommand;
import com.constants.ControllerConstants;
import com.controller.models.MessageModel;
import com.controller.types.AbstractController;
import com.dao.AccountDAO;
import com.dao.UserCredentialDAO;
import com.dao.UserDetailDAO;
import com.orm.UserDetail;

@Controller
@RequestMapping(value = ControllerConstants.SINGLE_PROFILE_PAGE_DIRECTORY)
public class SingleProfileController extends AbstractController {
	private static Logger logger = Logger.getLogger(SingleProfileController.class);

	@Autowired
	private UserDetailDAO userDetailDAO;
	@Autowired
	private UserCredentialDAO userCredentialDAO;
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public ModelAndView home(
			HttpServletResponse response,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String cookieValue,
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIdCookieValue,
			@PathVariable("userName") String userName) {
		logger.info("Getting the single Profile page! ");
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.SINGLE_PROFILE_PAGE_NAME);
		UserDetail detail=userDetailDAO.getUserDetail(userName);
		String password=userCredentialDAO.getPassword(userName);
		modelAndView.addObject("userDetail",detail );
		modelAndView.addObject("password",password);
		
		new PlaceNameInViewCommand(modelAndView, userName, this.userDetailDAO, true);
		
		return modelAndView;
	}
	
	/* Handles the "Transfer funds" functionality */
	@RequestMapping(value = "/{userName}", method = RequestMethod.POST, params="action=Update")
	public ModelAndView handleTransfer(
			HttpServletRequest request,
			HttpServletResponse response,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String cookieValue,
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIDcookieValue,
			@PathVariable("userName") String userName,
			final RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("redirect:" + ControllerConstants.MESSAGE_PAGE_DIRECTORY + "/" + userName);
		this.authenticateLogin(userName, cookieValue, modelAndView, response, sessionIDcookieValue);
		
		String fName=request.getParameter("fname");
		String lName=request.getParameter("lname");
		String password=request.getParameter("password");
		String emailID=request.getParameter("email");
		MessageModel model=new MessageModel();
		if(!StringUtils.isEmpty(fName) || !StringUtils.isEmpty(lName) || !StringUtils.isEmpty(emailID)){
			userDetailDAO.updateDetail(userName, fName, lName, emailID);
		}
		if(!StringUtils.isEmpty(password)){
			userCredentialDAO.updatePassword(userName, password);
		}
		model.setBody("");
		model.setHeader("Success");
		model.setErrorMessage("Update Success!");
		redirectAttributes.addFlashAttribute("message", model);
		return modelAndView;
	}
	
	
	/* Handles the functionality of the toolbar  */
	@RequestMapping(value = "/{userName}", method = RequestMethod.POST)
	public String handlePost(@RequestParam String action, @PathVariable("userName") String userName) {
		logger.info("profile information posted " + action);
		HandleToolbarClickCommand buttonHandler = new HandleToolbarClickCommand(action, userName);
		buttonHandler.execute();
		return buttonHandler.getOutput();
	}
	
}
