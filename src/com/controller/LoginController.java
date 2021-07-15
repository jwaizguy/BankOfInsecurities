package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.command.RegisterLoginCommand;
import com.constants.ControllerConstants;
import com.controller.models.LoginModel;
import com.dao.SessionDataDAO;
import com.dao.UserCredentialDAO;
import com.utility.UtilityFunctions;

@Controller
@RequestMapping(value={ControllerConstants.LOGIN_PAGE_DIRECTORY, ControllerConstants.HOME_DIRECTORY})
public class LoginController
{
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private SessionDataDAO sessionDataDAO;
	
	@Autowired
	private UserCredentialDAO userCredentialDAO; // DAO object will act as an interface to the Database

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home(
			@RequestParam(value="userName", required=false) String userName, 
			HttpServletResponse response, 
			HttpServletRequest request)
	{	
		String sessionID = request.getSession().getId();
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public String submit(
    		HttpServletResponse response, 
    		HttpServletRequest request, 
    		ModelMap modelMap, 
    		@ModelAttribute("loginModel") @Valid LoginModel loginModel, 
    		HttpServletRequest req,
    		@RequestParam(value="sessionID", required=false) String sessionID) {
		
		/*Model Attribute maps the variables in the .jsp "userName" & "password" to the
		 loginModel with the corresponding variables "userName" & password set to what was input */
		logger.info("in submit" + loginModel + " " + sessionID);
        
        if(sessionID == null) {
        	sessionID = request.getSession().getId();
        }
        
        if (userCredentialDAO.authenticate(loginModel.getUserName(), loginModel.getPassword())) {
        	logger.info("redirecting!");
        	String userName = loginModel.getUserName();
        	RegisterLoginCommand registerLoginCommand = new RegisterLoginCommand(sessionID, userName, this.sessionDataDAO);
        	registerLoginCommand.execute();
        	
        	UtilityFunctions.addAuthenticatedCookies(response, userName, sessionID);
            
        	return "redirect:" + ControllerConstants.ACCOUNT_MANAGEMENT_PAGE_NAME + "/" + userName;
        } else {
            modelMap.put("error", "Invalid UserName / Password");
            return "index";
        }
     }
}
