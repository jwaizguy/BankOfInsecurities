package com.controller;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.builders.StatementTableBuilder;
import com.command.HandleToolbarClickCommand;
import com.command.PlaceNameInViewCommand;
import com.constants.ControllerConstants;
import com.controller.types.AbstractController;
import com.dao.TransactionDAO;
import com.dao.UserDetailDAO;
import com.orm.Transaction;

@Controller
@RequestMapping(value = ControllerConstants.STATEMENT_DIRECTORY)
public class StatementController extends AbstractController {
	private StatementTableBuilder statementTableBuilder;
	private final String statementFilePathString = "statementFilepath";
	private final String downloadsPath = "/downloads";
	private final String statementFileName = "statement.txt";
	
	private static Logger logger = Logger.getLogger(StatementController.class);
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private UserDetailDAO userDetailDAO;
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public ModelAndView home(
			@PathVariable("userName") String userName, 
			HttpServletResponse response, 
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String cookieValue,
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIdCookieValue) {
		
		logger.info("Getting the contact information page! ");
		
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.STATEMENT_PAGE_NAME);
		
		new PlaceNameInViewCommand(modelAndView, userName, this.userDetailDAO, true);
		
		this.authenticateLogin(userName, cookieValue, modelAndView, response, sessionIdCookieValue);
		
		List<Transaction> transactions = this.transactionDAO.getTransactionHistoryInformation(userName);
		this.statementTableBuilder = new StatementTableBuilder(transactions);
		modelAndView.addObject("table0", statementTableBuilder.build());
		modelAndView.addObject(this.statementFilePathString, this.createLinkToFile(userName, this.statementFileName));
		
		this.fillDefaultFields(modelAndView, userName);
		
		return modelAndView;
	}
	
	private String createLinkToFile(String userName, String fileName) {
		return userName + this.downloadsPath + "/" + fileName;
	}
	
	@RequestMapping(value = "{userName}/downloads/{fileName}", method = RequestMethod.GET)
	public void home(HttpServletResponse response, @PathVariable("userName") String userName, @PathVariable("fileName") String fileName) {
		logger.info("Downloading the " + fileName  + "!");
		
		List<Transaction> transactions = this.transactionDAO.getTransactionHistoryInformation(userName);
		this.statementTableBuilder = new StatementTableBuilder(transactions);
		File file = new File(userName + ".txt");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write("sensitive data only meant for: " + userName);
			bw.newLine();
			bw.write(this.statementTableBuilder.toString());
			bw.close();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if(mimeType == null) {
			mimeType = "application/octet-stream";
		}
		
		response.setContentType(mimeType);
		
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
		response.setContentLength((int)file.length());
		InputStream inputStream;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.POST)
	public String handlePost(@RequestParam(required = false) String action, @PathVariable("userName") String userName) {
		logger.info("contact information posted " + action);
		HandleToolbarClickCommand buttonHandler = new HandleToolbarClickCommand(action, userName);
		buttonHandler.execute();
		return buttonHandler.getOutput();
	}
}
