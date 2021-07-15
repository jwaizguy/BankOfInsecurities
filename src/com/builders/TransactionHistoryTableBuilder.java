package com.builders;

import java.text.SimpleDateFormat;
import java.util.List;

import com.orm.Transaction;
import com.utility.toStringConversions.DollarConversions;

public class TransactionHistoryTableBuilder extends AbstractTableBuilder {
	private final TableEntry IDLabel = new TableEntry("ID");
	private final TableEntry transactionWithLabel = new TableEntry("Transaction With");
	private final TableEntry amountLabel = new TableEntry("Amount");
	private final TableEntry transactionTypeLabel = new TableEntry("Transaction Type");
	private final TableEntry transactionTimeLabel = new TableEntry("Transaction Time");
	
	private final TableEntry[] headerValues = {IDLabel, transactionWithLabel, amountLabel, transactionTypeLabel, transactionTimeLabel};
	
	private DollarConversions dollarConversions = new DollarConversions();
	
	public TransactionHistoryTableBuilder(final List<Transaction> accountEntries) {
		this.headerlessTable = this.extractTableBody(accountEntries);
		this.createFullTable(this.headerValues, this.headerlessTable);
		
		this.tableWidth *= 2;
	}
	
	private TableEntry[][] extractTableBody(final List<Transaction> transactions) {
		TableEntry[][] outputTable = new TableEntry[transactions.size()][this.headerValues.length];
		for(int i = 0; i < transactions.size(); i++) {
			outputTable[i] = this.extractCurrentEntriesData(transactions.get(i));
		}
		
		return outputTable;
	}
	
	private TableEntry[] extractCurrentEntriesData(Transaction transaction) {
		TableEntry[] output = new TableEntry[this.headerValues.length];
		
		for(int i = 0; i < this.headerValues.length; i++) {
			if(this.headerValues[i].equals(this.IDLabel)) {
				output[i] = new TableEntry(transaction.getTransactionID());
			} else if(this.headerValues[i].equals(this.transactionWithLabel)) {
				output[i] = new TableEntry(transaction.getTransactionWith());
			} else if(this.headerValues[i].equals(this.amountLabel)) {
				output[i] = new TableEntry(this.dollarConversions.convertDoubleToType(DollarConversions.doubleOptions.normal, transaction.getTransactionAmount()));
			} else if(this.headerValues[i].equals(this.transactionTypeLabel)) {
				output[i] = new TableEntry(Character.toString(transaction.getTransactionType()));
			} else if(this.headerValues[i].equals(this.transactionTimeLabel)) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss SSS");
				output[i] = new TableEntry(sdf.format(transaction.getTransactionTime()));
			}
		}
		
		return output;
	}

	@Override
	public HtmlTable build() {
		return new HtmlTable(this);
	}
}
