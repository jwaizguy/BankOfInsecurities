package com.command.checkCommand;

import com.orm.Account;

public class IsSameAccountsCheck implements CheckCommand {
	private final String errorMessage = "You can't transfer accounts from an account to itsself";
	private Account account1;
	private Account account2;
	
	public IsSameAccountsCheck(Account account1, Account account2) {
		this.account1 = account1;
		this.account2 = account2;
	}

	@Override
	public boolean checkIsValid() {
		return this.account1.getAccountID() != this.account2.getAccountID();
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
