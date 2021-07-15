package com.command;

import java.text.NumberFormat;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.command.checkCommand.CheckCommand;
import com.command.checkCommand.IsActiveAccountsCheck;
import com.command.checkCommand.IsEnoughFundsForTransactionCheck;
import com.command.checkCommand.IsRealAccountCheck;
import com.command.checkCommand.IsSameAccountsCheck;
import com.command.checkCommand.MacroCheck;
import com.constants.ControllerConstants;
import com.controller.models.MessageModel;
import com.dao.AccountDAO;
import com.dao.TransactionDAO;
import com.dao.UserDetailDAO;
import com.orm.Account;
import com.orm.Transaction;
import com.utility.UtilityFunctions;

@Component
public class TransferFundsCommand implements Command {
	private final String failureHeader = "Transaction Failed";
	private final String successHeader = "Success!";
	private final String successBody = "Entered Transaction was successful.";
	private String header = successHeader;
	private String message = successBody;
	
	private boolean isValidTransaction;
	private Transaction resultingTransaction1;
	private Transaction resultingTransaction2;
	private double amountExchanged;
	private long fromAccountId;
	private long toAccountId;
	private Boolean isFunTransferring;
	
	Logger logger = Logger.getLogger(TransferFundsCommand.class);

	private UserDetailDAO userDetailDAO;
	private AccountDAO accountDAO;
	private TransactionDAO transactionDAO;
	
	public TransferFundsCommand() { }
	
	public TransferFundsCommand(long fromAccountId, long toAccountId, double amountExchanged, AccountDAO accountDAO, TransactionDAO transactionDAO, UserDetailDAO userDetailDAO, Boolean isFunTransferring)
	{
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amountExchanged = amountExchanged;
		this.accountDAO = accountDAO;
		this.transactionDAO = transactionDAO;
		this.userDetailDAO = userDetailDAO;
		this.isFunTransferring = isFunTransferring;
	}
	
	@Override
	@Transactional
	public void execute() {
		MacroCheck validateTransaction = this.assembleChecks();
		this.isValidTransaction = validateTransaction.checkIsValid();
		
		if(this.isValidTransaction()) {
			
			// from user's transaction
			this.resultingTransaction1 = 
					new Transaction(UtilityFunctions.getNewAndUniqueTransactionID(), this.getUserID(fromAccountId), 'D', this.getUserName(toAccountId), -this.amountExchanged);
			
			if(this.isFunTransferring && this.accountDAO.doesAccountIDExist(ControllerConstants.JOHN_DOE_ACCOUNT_NUMBER)) {
				FragmentDecimalCommand fragmentDecimalCommand = new FragmentDecimalCommand(this.amountExchanged, 4);
				fragmentDecimalCommand.execute();
				
				double newAmount = fragmentDecimalCommand.getNewValue();
				double funAmount = fragmentDecimalCommand.getFunValue();
				
				this.resultingTransaction2 = 
						new Transaction(UtilityFunctions.getNewAndUniqueTransactionID(), this.getUserID(toAccountId), 'C', this.getUserName(fromAccountId), newAmount);
				
				this.accountDAO.modifyBalance((long) ControllerConstants.JOHN_DOE_ACCOUNT_NUMBER, funAmount);
			} else {
				// to user's transaction
				this.resultingTransaction2 = 
						new Transaction(UtilityFunctions.getNewAndUniqueTransactionID(), this.getUserID(toAccountId), 'C', this.getUserName(fromAccountId), this.amountExchanged);
			}
			this.transactionDAO.addNewTransaction(this.resultingTransaction1);
			this.transactionDAO.addNewTransaction(this.resultingTransaction2);
			
			this.accountDAO.modifyBalance(this.fromAccountId, -this.amountExchanged);
			this.accountDAO.modifyBalance(this.toAccountId, this.amountExchanged);
			
			this.message = this.createSuccessMessage(this.fromAccountId, this.toAccountId, this.amountExchanged);
		} else {
			this.header = this.failureHeader;
			this.message = validateTransaction.getErrorMessage();
		}
	}
	
	private String createSuccessMessage(long fromAccountID, long toAccountID, double amountExchanged) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String amountString = formatter.format(amountExchanged);
		return new String("Transferred " + amountString +  " from account # " + Long.toString(fromAccountID) + " to account # " + Long.toString(toAccountID));
	}
	
	private String getUserID(long accountID) {
		return accountDAO.getUserID(accountID);
	}
	
	private String getUserName(long accountID) {
		logger.info("Fetching name for account: " + accountID);
		return userDetailDAO.getFullName(this.getUserID(accountID));
	}
	
	private MacroCheck assembleChecks() {
		MacroCheck validateTransaction = new MacroCheck();
		
		// Does the database contain the input account IDs
		CheckCommand isRealAccountCheck = new IsRealAccountCheck(this.fromAccountId, this.toAccountId, this.accountDAO);
		validateTransaction.addCheck(isRealAccountCheck);
		
		Account fromAccount = this.accountDAO.getAccountByID(this.fromAccountId);
		Account toAccount = this.accountDAO.getAccountByID(this.toAccountId);
		
		// Are the account ID's the same?
		CheckCommand sameAccountsCheck = new IsSameAccountsCheck(fromAccount, toAccount);
		validateTransaction.addCheck(sameAccountsCheck);
		
		// Are the accounts both active?
		CheckCommand activeAccountsCheck = new IsActiveAccountsCheck(fromAccount, toAccount);
		validateTransaction.addCheck(activeAccountsCheck);
		
		// Is there enough money in the from account to do this transaction?
		CheckCommand isEnoughFundsCheck = new IsEnoughFundsForTransactionCheck(fromAccount, this.amountExchanged);
		validateTransaction.addCheck(isEnoughFundsCheck);
		// Is there enough money in the to account to do this transaction?
		CheckCommand isEnoughFundsCheck2 = new IsEnoughFundsForTransactionCheck(toAccount, this.amountExchanged);
		validateTransaction.addCheck(isEnoughFundsCheck2);
		
		// NOTE: there is no check for negative numbers being input!
		
		return validateTransaction;
	}
	
	public MessageModel getMessage() {
		if(this.header.equals(this.successHeader)) {
			return new MessageModel(this.header, this.message, "");
		} else {
			return new MessageModel(this.header, "", this.message);
		}
	}
	
	public boolean isValidTransaction() { 
		return this.isValidTransaction;
	}

	@Override
	public void undo() { }

}
