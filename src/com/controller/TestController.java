package com.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.constants.ControllerConstants;
import com.controller.types.AbstractController;

@Controller
@RequestMapping(value = ControllerConstants.ADMIN_MESSAGE_DIRECTORY)
public class TestController extends AbstractController {
	private static Logger logger = Logger.getLogger(TestController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home() {
		logger.info("Getting the admin information page! ");
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.ADMIN_MESSAGE_PAGE_NAME);
		
		return modelAndView;
	}
}
