package com.orm;
// To map transaction-related class and functions in this project to the database
import java.sql.Timestamp;

public class Transaction
{
	private String transactionID;
	private String userID;
	private char transactionType;
	private String transactionWith;
	private double transactionAmount;
	private Timestamp transactionTime;
	
	public Transaction() {}
	
	public Transaction(String transactionID, String userID, char transactionType, String transactionWith, double transactionAmount) {
		this.transactionID = transactionID;
		this.userID = userID;
		this.transactionType = transactionType;
		this.transactionWith = transactionWith;
		this.transactionAmount = transactionAmount;
	}
	
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public char getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(char transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionWith() {
		return transactionWith;
	}
	public void setTransactionWith(String transactionWith) {
		this.transactionWith = transactionWith;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Timestamp getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Timestamp transactionTime) {
		this.transactionTime = transactionTime;
	}
}
