package com.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.constants.ControllerConstants;

/** This allows all of the different controllers to handle clicks on the various buttons without creating duplicate code. **/
public class HandleToolbarClickCommand implements Command {
	// These names correspond to the variables in accountManagement.jsp's button names
	private final String accounts = "Accounts";
	private final String payments = "Payments";
	private final String statements = "Statements";
	private final String contact = "Contact";
	private final String profile = "Profile";
	private final String adminPage = "AdminPage";
	private final String[] actions = {accounts, payments, statements, contact, profile, adminPage};
	
	private String userName;
	private String action;
	private String outputDirectory;
		
	public HandleToolbarClickCommand(String action, String userName) {
		this.action = action;
		this.userName = userName;
	}

	@Override
	public void execute() {
		// set the default value
		this.outputDirectory = "redirect:" + ControllerConstants.ACCOUNT_MANAGEMENT_DIRECTORY + "/" + this.userName;
		
		if(action.equals(this.accounts)) {
			this.outputDirectory = "redirect:" + ControllerConstants.ACCOUNT_MANAGEMENT_DIRECTORY + "/" + this.userName;
		} else if(action.equals(this.payments)) {
			this.outputDirectory = "redirect:" + ControllerConstants.TRANSACTION_HISTORY_DIRECTORY + "/" + this.userName;
		} else if(action.equals(this.statements)) {
			this.outputDirectory = "redirect:" + ControllerConstants.STATEMENT_DIRECTORY + "/" + this.userName;
		} else if(action.equals(this.contact)) {
			this.outputDirectory = "redirect:" + ControllerConstants.CONTACT_INFORMATION_DIRECTORY + "/" + this.userName;
		} else if(action.equals(this.profile)) {
			this.outputDirectory = "redirect:" + ControllerConstants.PROFILE_DIRECTORY + "/" + this.userName;
		} else if(action.equals(this.adminPage)) {
			this.outputDirectory = "redirect:" + ControllerConstants.ADMIN_DASHBOARD_DIRECTORY + "/" + this.userName;
		}
	}
	public String getOutput() {
		return this.outputDirectory;
	}
	
	public List<String> getActions() {
		return new ArrayList<String>(Arrays.asList(this.actions));
	}

	@Override
	public void undo() { }
}
