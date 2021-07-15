package com.command.checkCommand;

import com.orm.Account;

public class IsEnoughFundsForTransactionCheck implements CheckCommand {
	private final String errorMessage = "Not enough funds in your account to complete this transaction!";
	private Account fromAccount;
	private double amount;
	
	public IsEnoughFundsForTransactionCheck(Account fromAccount, double amount) {
		this.fromAccount = fromAccount;
		this.amount = amount;
	}

	@Override
	public boolean checkIsValid() {
		if(this.fromAccount.getAccountBalance() - amount >= 0.00) {
			return true;
		}
		return false;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	
}
