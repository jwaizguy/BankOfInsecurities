package com.orm;
// object-relation mapping between the Account class and the account in the postgre database
import java.sql.Date;

public class Account
{
	private long accountID;
	private char accountType;
	private String userID;
	private double accountBalance;
	private Date created;
	private boolean isActive;
	
	public Account() { }
	
	public Account(long accountID, char accountType, String userID, double accountBalance, Date created, boolean isActive) {
		this.accountID = accountID;
		this.accountType = accountType;
		this.userID = userID;
		this.accountBalance = accountBalance;
		this.created = created;
		this.isActive = isActive;
	}
	
	public long getAccountID() {
		return accountID;
	}
	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}
	public char getAccountType() {
		return accountType;
	}
	public void setAccountType(char accountType) {
		this.accountType = accountType;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isBoolean) {
		this.isActive = isBoolean;
	}
}
