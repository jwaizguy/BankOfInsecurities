package com.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.command.HandleToolbarClickCommand;
import com.command.PlaceNameInViewCommand;
import com.command.TransferFundsCommand;
import com.constants.ControllerConstants;
import com.controller.models.TransferFundsModel;
import com.controller.types.AbstractController;
import com.dao.AccountDAO;
import com.dao.TransactionDAO;
import com.dao.UserDetailDAO;
import com.orm.Account;



@Controller
@RequestMapping(value = ControllerConstants.SINGLE_ACCOUNT_MANAGEMENT_PAGE_DIRECTORY)
public class SingleAccountController extends AbstractController {
	private final String tableLabel = "contactInformation";
	private final String userNameParameter = "userName";
	private final int numTableCols = 4; 
	private static Logger logger = Logger.getLogger(SingleAccountController.class);

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private TransactionDAO transactionDAO;

	@Autowired
	private UserDetailDAO userDetailDAO;
	
	@Autowired
	@Qualifier("funMoneyTransferring")
	private Boolean isFunTransferring;

	@RequestMapping(value = "/{userName}/{accountNumber}", method = RequestMethod.GET)
	public ModelAndView home(
			HttpServletResponse response,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String cookieValue,
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIdCookieValue,
			@PathVariable("accountNumber") long accountNumber,
			@PathVariable("userName") String userName) {
		
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.SINGLE_ACCOUNT_MANAGEMENT_PAGE_NAME);
		
		new PlaceNameInViewCommand(modelAndView, userName, this.userDetailDAO, true);
		
		this.authenticateLogin(userName, cookieValue, modelAndView, response, sessionIdCookieValue);
		logger.info("Getting the account number details page! ");

		Account account = this.accountDAO.getAccountByID(accountNumber);
		modelAndView.addObject("displayAccount", account);

		return modelAndView;
	}

	/* Handles the "Transfer funds" functionality */
	@RequestMapping(value = "/{userName}/{accountNumber}", method = RequestMethod.POST, params="action=Transfer")
	public ModelAndView handleTransfer(
			HttpServletResponse response,
			@CookieValue(value = ControllerConstants.LOGIN_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String cookieValue,
			@CookieValue(value = ControllerConstants.SESSION_ID_COOKIE_NAME, defaultValue = ControllerConstants.NULL_COOKIE) String sessionIDcookieValue,
			@PathVariable("accountNumber") long accountNumber, 
			@PathVariable("userName") String userName,
			@ModelAttribute("purposedTransfer") @Valid TransferFundsModel proposedTransfer,
			final RedirectAttributes redirectAttributes) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:" + ControllerConstants.MESSAGE_PAGE_DIRECTORY + "/" + userName);
		this.authenticateLogin(userName, cookieValue, modelAndView, response, sessionIDcookieValue);
		
		logger.info("Transferring funds! amount: " + proposedTransfer.getAmount() + " Account# " + proposedTransfer.getToAccountNumber());


		TransferFundsCommand transferFundsCommand = 
				new TransferFundsCommand(accountNumber, proposedTransfer.getToAccountNumber(), proposedTransfer.getAmount(), this.accountDAO, this.transactionDAO, this.userDetailDAO, this.isFunTransferring);
		transferFundsCommand.execute();

		logger.info("success/fail: " + transferFundsCommand.getMessage().getBody());

		
		redirectAttributes.addFlashAttribute("message", transferFundsCommand.getMessage());

		return modelAndView;
	}

	/* Handles the functionality of the toolbar  */
	@RequestMapping(value = "/{userName}/{accountNumber}", method = RequestMethod.POST)
	public String handlePost(@RequestParam String action, @PathVariable("accountNumber") String accountNumber, @PathVariable("userName") String userName) {
		logger.info("contact information posted " + action);
		HandleToolbarClickCommand buttonHandler = new HandleToolbarClickCommand(action, userName);
		buttonHandler.execute();
		return buttonHandler.getOutput();
	}
}