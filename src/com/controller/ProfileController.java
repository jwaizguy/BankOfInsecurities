package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.command.HandleToolbarClickCommand;
import com.command.PlaceNameInViewCommand;
import com.constants.ControllerConstants;
import com.controller.types.AbstractController;
import com.dao.UserDetailDAO;
import com.orm.UserDetail;

@Controller
@RequestMapping(value = ControllerConstants.PROFILE_DIRECTORY)
public class ProfileController extends AbstractController {
private static Logger logger = Logger.getLogger(ProfileController.class);

	@Autowired
	private UserDetailDAO userDetailDAO;
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("userName") String userName,
			HttpServletResponse response,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String cookieValue, 
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIDValue) {
		
		logger.info("Getting the Profile page! ");
		
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.PROFILE_PAGE_NAME);
		
		if(this.authenticateLogin(userName, cookieValue, modelAndView, response, sessionIDValue)) {
			UserDetail detail=userDetailDAO.getUserDetail(userName);
			modelAndView.addObject("userDetail",detail );
			new PlaceNameInViewCommand(modelAndView, userName, this.userDetailDAO, true);
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.POST)
	public ModelAndView handlePost(@RequestParam(required = false) String action, @PathVariable("userName") String userName) {
		logger.info("profile information posted " + action);
		HandleToolbarClickCommand buttonHandler;
		if(action != null) {
			buttonHandler = new HandleToolbarClickCommand(action, userName);
		} else {
			String newPath = "redirect:" + 
					ControllerConstants.SINGLE_PROFILE_PAGE_DIRECTORY +
					"/" + userName;
			ModelAndView modelAndView = new ModelAndView(newPath);
			buttonHandler = new HandleToolbarClickCommand("Profile", userName);
			return modelAndView;
		}
		buttonHandler.execute();
		return new ModelAndView(buttonHandler.getOutput());
	}
}

