package com.builders;

import java.text.NumberFormat;
import java.util.List;

import com.constants.ControllerConstants;
import com.orm.Account;

public class AccountBalancesTableBuilder extends AbstractTableBuilder {
	
	private final String userName;
	private final TableEntry AccountIDText = new TableEntry("Account ID");
	private final TableEntry accountBalanceText = new TableEntry("Account Balance");
	
	private final TableEntry[] headerValues = {AccountIDText, accountBalanceText};
	
	public AccountBalancesTableBuilder(final List<Account> accounts, final String userName) {
		this.userName = userName;
		this.headerlessTable = this.extractTableBody(accounts);
		this.createFullTable(this.headerValues, this.headerlessTable);
	}
	
	private TableEntry[][] extractTableBody(final List<Account> transactions) {
		TableEntry[][] outputTable = new TableEntry[transactions.size()][this.headerValues.length];
		for(int i = 0; i < transactions.size(); i++) {
			outputTable[i] = this.extractCurrentEntriesData(transactions.get(i));
		}
		
		return outputTable;
	}
	
	private TableEntry[] extractCurrentEntriesData(Account account) {
		TableEntry[] output = new TableEntry[this.headerValues.length];
		
		for(int i = 0; i < this.headerValues.length; i++) {
			if(this.headerValues[i].equals(this.AccountIDText)) {
				String accountIdString = Long.toString(account.getAccountID());
				output[i] = new TableEntry(
						accountIdString, 
						ControllerConstants.SINGLE_ACCOUNT_MANAGEMENT_PAGE_DIRECTORY + "/" + this.userName + "/" + accountIdString);
			} else if(this.headerValues[i].equals(this.accountBalanceText)) {
				double value = account.getAccountBalance();
				NumberFormat formatter = NumberFormat.getCurrencyInstance();
				String currency = formatter.format(value);
				output[i] = new TableEntry(currency);
			}
		}
		
		return output;
	}

	@Override
	public HtmlTable build() {
		return new HtmlTable(this);
	}

}
