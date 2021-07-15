package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.ServletContext;
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

import com.builders.AdminNavLinkBuilder;
import com.command.fileManagement.LoadUploadedFileCommand;
import com.command.fileManagement.SendFileInResponseCommand;
import com.constants.ControllerConstants;
import com.controller.models.MessageToAdminModel;
import com.controller.types.AbstractAdminPage;
import com.dao.MessageToAdminDAO;
import com.orm.UserMessage;

@Controller
@RequestMapping(ControllerConstants.ADMIN_MESSAGE_DIRECTORY)
public class AdminMessengerController extends AbstractAdminPage {
	
	private static Logger logger = Logger.getLogger(AdminMessengerController.class);
	private AdminNavLinkBuilder adminNavLinkBuilder;
	private final String downloadsPath = "/downloads";
	
	@Autowired
	ServletContext context;
	
	@Autowired
	MessageToAdminDAO messageToAdminDAO;
	
	public AdminMessengerController() {
		this.adminNavLinkBuilder = new AdminNavLinkBuilder();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{userName}")
	public ModelAndView handleGet(
			@PathVariable("userName") String userName,
			HttpServletResponse response,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String usernameCookieValue, 
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIDValue) {
		
		this.adminNavLinkBuilder.init(userName, ControllerConstants.ADMIN_MESSAGE_DIRECTORY);
		
		List<UserMessage> messages = this.messageToAdminDAO.getAllMessages();
		
		logger.info("Getting the admin messenger page! ");
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.ADMIN_MESSAGE_PAGE_NAME);
		
		modelAndView.addObject("uploadFilePath", this.createLinkToAttachment(userName));
		modelAndView.addObject("messages", messages);
		if(this.authenticateLogin(userName, usernameCookieValue, modelAndView, response, sessionIDValue)) {
			modelAndView.addObject("navLinks", this.adminNavLinkBuilder.getLinks());
		}
		
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{userName}/downloads/{filename}.{filenameExt}")
	public void downloadFile(
			@PathVariable("userName") String userName,
			@PathVariable("filename") String filename,
			@PathVariable("filenameExt") String filenameExt,
			HttpServletResponse response,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String usernameCookieValue, 
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIDValue) {
		
		String realFileName = filename + "." + filenameExt;
		logger.info("Trying to get: " + realFileName);
		
		LoadUploadedFileCommand fileLoader = new LoadUploadedFileCommand(realFileName, this.context);
		fileLoader.execute();
		if(fileLoader.getOutputFile().exists()) {
			SendFileInResponseCommand fileSender = new SendFileInResponseCommand(fileLoader.getOutputFile(), response);
			fileSender.execute();
			logger.info("the file does exist!");
		}
	}
	
	// delete a given message
	@RequestMapping(method = RequestMethod.POST, value="/{userName}")
	public ModelAndView deleteMessage(@PathVariable("userName") String userName, @RequestParam String action) {
	
		UserMessage message = this.messageToAdminDAO.getUserMessageByID(action);
		
		
		// delete the attached file (if it exists)
		if(! message.getFileName().equals(MessageToAdminModel.nullFileString)) {
			LoadUploadedFileCommand fileLoader = new LoadUploadedFileCommand(message.getFileName(), this.context);
			fileLoader.execute();
			File file = fileLoader.getOutputFile();
			
			
			if(file != null && file.exists()) {
				try {
					Files.delete(file.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// delete from the database
		this.messageToAdminDAO.deleteUserMessage(message.getMsgID());
		
		return new ModelAndView("redirect:" + ControllerConstants.ADMIN_MESSAGE_DIRECTORY + "/" + userName);
	}
	
	private String createLinkToAttachment(String userName) {
		return userName + this.downloadsPath + "/";
	}
}
