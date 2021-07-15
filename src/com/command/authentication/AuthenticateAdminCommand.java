package com.command.authentication;

import com.command.PermanentCommand;
import com.dao.UserDetailDAO;

public class AuthenticateAdminCommand implements PermanentCommand {

	UserDetailDAO userDetailDao;
	private String username;
	private boolean result;
	
	public AuthenticateAdminCommand(UserDetailDAO userDetailDao, String username) {
		this.userDetailDao = userDetailDao;
		this.username = username;
	}
	
	@Override
	public void execute() {
		this.result = this.userDetailDao.isAdmin(this.username);
	}
	
	public boolean isSuccessful() {
		return this.result;
	}
	
}
