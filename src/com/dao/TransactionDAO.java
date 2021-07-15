package com.dao;

import java.util.List;

import com.orm.Transaction;

public interface TransactionDAO
{
	public void addNewTransaction(Transaction transaction);
	public List<Transaction> getTransactionHistoryInformation(String userID);
}
