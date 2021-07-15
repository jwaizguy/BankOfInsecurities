package com.command.checkCommand;

import com.dao.AccountDAO;

public class IsRealAccountCheck implements CheckCommand {
	private final String errorMessage = "The entered account ID is not valid.";
	private long account1Id;
	private long account2Id;
	private AccountDAO accountDAO;
	
	public IsRealAccountCheck(long account1Id, long account2Id, AccountDAO accountDAO) {
		this.account1Id = account1Id;
		this.account2Id = account2Id;
		this.accountDAO = accountDAO;
	}
	
	@Override
	public boolean checkIsValid() {
		if(this.accountDAO.getAccountByID(this.account1Id) == null || this.accountDAO.getAccountByID(this.account2Id) == null) {
			return false;
		}
		return true;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}

}
