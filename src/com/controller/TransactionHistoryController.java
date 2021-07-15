package com.controller;

import java.util.List;

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

import com.builders.TransactionHistoryTableBuilder;
import com.command.HandleToolbarClickCommand;
import com.command.PlaceNameInViewCommand;
import com.constants.ControllerConstants;
import com.controller.types.AbstractController;
import com.dao.TransactionDAO;
import com.dao.UserDetailDAO;
import com.orm.Transaction;

@Controller
@RequestMapping(value = ControllerConstants.TRANSACTION_HISTORY_DIRECTORY)
public class TransactionHistoryController extends AbstractController {
	private final String tableLabel = "transactionHistory";
	private final String userNameParameter = "userName";
	private final int numTableCols = 4; 
	private static Logger logger = Logger.getLogger(TransactionHistoryController.class);

	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private UserDetailDAO userDetailDAO;

	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public ModelAndView home(
			HttpServletResponse response,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String cookieValue,
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIdCookieValue,
			@PathVariable("userName") String userName) {
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.TRANSACTION_HISTORY_PAGE_NAME);
		
		new PlaceNameInViewCommand(modelAndView, userName, this.userDetailDAO, true);
		
		logger.info("Getting the transaction history page! ");
		this.fillDefaultFields(modelAndView, userName);

		List<Transaction> transactions = this.transactionDAO.getTransactionHistoryInformation(userName);
		TransactionHistoryTableBuilder transactionHistoryTableBuilder = new TransactionHistoryTableBuilder(transactions);
		modelAndView.addObject("table0", transactionHistoryTableBuilder.build());
		
		return modelAndView;
	}

	@RequestMapping(value = "/{userName}", method = RequestMethod.POST)
	public String handlePost(@RequestParam String action, @PathVariable("userName") String userName) {
		logger.info("Transaction history posted " + action);
		HandleToolbarClickCommand buttonHandler = new HandleToolbarClickCommand(action, userName);
		buttonHandler.execute();
		return buttonHandler.getOutput();
	}
	
}
