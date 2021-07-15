package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.builders.AccountBalancesTableBuilder;
import com.command.HandleToolbarClickCommand;
import com.command.PlaceNameInViewCommand;
import com.constants.ControllerConstants;
import com.controller.types.AbstractController;
import com.dao.AccountDAO;
import com.dao.UserDetailDAO;
import com.orm.Account;

@Controller
@RequestMapping(value = ControllerConstants.ACCOUNT_MANAGEMENT_DIRECTORY)
public class AccountManagementController extends AbstractController {
	private static Logger logger = Logger.getLogger(AccountManagementController.class);

	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private UserDetailDAO userDetailDAO;

	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public ModelAndView handleGet(
			HttpServletResponse response,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String cookieValue, 
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIDValue, 
			@PathVariable("userName") String userName) {
		
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.ACCOUNT_MANAGEMENT_PAGE_NAME);
		if(this.authenticateLogin(userName, cookieValue, modelAndView, response, sessionIDValue)) {
			new PlaceNameInViewCommand(modelAndView, userName, this.userDetailDAO, true);
			this.fillDefaultFields(modelAndView, userName);
			this.initTable(modelAndView, userName);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/{userName}", method = RequestMethod.POST)
	public ModelAndView handlePost(
			@RequestParam(required = false) String action, 
			@PathVariable("userName") String userName,
			@ModelAttribute Account selectedAccount) {
		logger.info("Posted " + action);
		HandleToolbarClickCommand buttonHandler;
		if(action != null) {
			// button press
			buttonHandler = new HandleToolbarClickCommand(action, userName);
		} else {
			// dropdown event
			logger.info("Posted acct # " + selectedAccount.getAccountID());

			String newPath = "redirect:" + 
					ControllerConstants.SINGLE_ACCOUNT_MANAGEMENT_PAGE_DIRECTORY +
					"/" + userName +
					"/" + selectedAccount.getAccountID();

			ModelAndView modelAndView = new ModelAndView(newPath);
			modelAndView.addObject("dropDownOptions", selectedAccount);
			buttonHandler = new HandleToolbarClickCommand("Accounts", userName);
			return modelAndView;
		}
		buttonHandler.execute();
		return new ModelAndView(buttonHandler.getOutput());

	}
	
	private void initDropDown(ModelAndView modelAndView, List<Account> transactions) {
		List<Long> options = new ArrayList<Long>();
		
		for(Account account : transactions) {
			options.add(account.getAccountID());
		}

		modelAndView.addObject("dropDownOptions", options);
		modelAndView.addObject("selectedAccount", new Account());
	}

	private void initTable(ModelAndView modelAndView, String userName) {
		List<Account> transactions = this.accountDAO.getAccountsByUserID(userName);
		AccountBalancesTableBuilder transactionHistoryTableBuilder = new AccountBalancesTableBuilder(transactions, userName);

		modelAndView.addObject("table0", transactionHistoryTableBuilder.build());
		
		this.initDropDown(modelAndView, transactions);
	}
}
