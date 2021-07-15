package com.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.command.HandleToolbarClickCommand;
import com.command.PlaceNameInViewCommand;
import com.command.fileManagement.SaveFileCommand;
import com.constants.ControllerConstants;
import com.controller.models.MessageToAdminModel;
import com.controller.types.AbstractController;
import com.dao.MessageToAdminDAO;
import com.dao.UserDetailDAO;
import com.orm.UserMessage;



@Controller
@RequestMapping(value = ControllerConstants.CONTACT_INFORMATION_DIRECTORY)
public class ContactInformationController extends AbstractController {
	private final String tableLabel = "contactInformation";
	private final String userNameParameter = "userName";
	private final int numTableCols = 4; 
	private static Logger logger = Logger.getLogger(ContactInformationController.class);
	
	@Autowired
	private UserDetailDAO userDetailDAO;
	
	@Autowired 
	MessageToAdminDAO messageToAdminDAO;
	
	@Autowired
	ServletContext context;
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public ModelAndView home(
			@PathVariable("userName") String userName,
			HttpServletResponse response,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String cookieValue, 
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIDValue) {
		
		logger.info("Getting the contact information page! ");
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTACT_INFORMATION_PAGE_NAME);
		if(this.authenticateLogin(userName, cookieValue, modelAndView, response, sessionIDValue)) {
			this.fillDefaultFields(modelAndView, userName);
			new PlaceNameInViewCommand(modelAndView, userName, this.userDetailDAO, true);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.POST)
	public ModelAndView handlePost(
			@RequestParam(required = false) String action, 
			@PathVariable("userName") String userName,
			@ModelAttribute("messageToAdminModel") @Valid MessageToAdminModel messageToAdminModel) {
		logger.info("contact information posted " + action);
		
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTACT_INFORMATION_PAGE_NAME);
		
		if(action != null) {
			HandleToolbarClickCommand buttonHandler = new HandleToolbarClickCommand(action, userName);
			buttonHandler.execute();
			modelAndView.setViewName(buttonHandler.getOutput());
		} else {
			SaveFileCommand saveFileCommand = new SaveFileCommand(ControllerConstants.CONTACT_INFORMATION_DIRECTORY, messageToAdminModel.getMultipartFile(), this.context);
			saveFileCommand.execute();
			messageToAdminModel.setSentBy(userName);

			UserMessage userMessage = new UserMessage(messageToAdminModel);
			this.messageToAdminDAO.saveMessage(userMessage);
			
			messageToAdminModel.setSentBy(userName);
			this.fillDefaultFields(modelAndView, userName);
			logger.info("contact information posted " + messageToAdminModel.toString());
		}
		return modelAndView;
	}
}
