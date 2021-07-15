package com.command.checkCommand;

import com.orm.Account;

public class IsActiveAccountsCheck implements CheckCommand {
	private final String errorMessage = "One of the accounts is not active.";
	private Account account1;
	private Account account2;
	
	public IsActiveAccountsCheck(Account account1, Account account2) {
		this.account1 = account1;
		this.account2 = account2;
	}
	
	@Override
	public boolean checkIsValid() {
		return (this.account1.getIsActive() && this.account2.getIsActive());
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}

}
