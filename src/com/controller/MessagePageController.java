package com.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.command.HandleToolbarClickCommand;
import com.constants.ControllerConstants;
import com.controller.models.MessageModel;

@Controller
@RequestMapping(value = ControllerConstants.MESSAGE_PAGE_DIRECTORY)
public class MessagePageController {
	private static Logger logger = Logger.getLogger(ContactInformationController.class);
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("userName") String userName, @ModelAttribute("message") MessageModel message) {
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.MESSAGE_PAGE_NAME);
		
		modelAndView.addObject("message", message);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.POST)
	public String handlePost(@RequestParam String action, @PathVariable("userName") String userName) {
		logger.info("contact information posted " + action);
		HandleToolbarClickCommand buttonHandler = new HandleToolbarClickCommand(action, userName);
		buttonHandler.execute();
		return buttonHandler.getOutput();
	}
}
