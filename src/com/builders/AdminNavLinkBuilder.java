package com.builders;

import java.util.LinkedList;
import java.util.List;

import com.constants.ControllerConstants;

public class AdminNavLinkBuilder {
	private final String logoutText = "Logout";
	
	private AdminNavLink messageLink = new AdminNavLink("Messenger", ControllerConstants.ADMIN_MESSAGE_DIRECTORY);
	private AdminNavLink dashboardLink = new AdminNavLink("Dashboard", ControllerConstants.ADMIN_DASHBOARD_DIRECTORY);
	private AdminNavLink bankAccountLink = new AdminNavLink("Bank Account", ControllerConstants.ACCOUNT_MANAGEMENT_DIRECTORY);
	private AdminNavLink logoutLink = new AdminNavLink(logoutText, "/redirect/login");
	
	private AdminNavLink[] adminNavLinks = {messageLink, dashboardLink, bankAccountLink, logoutLink};
	
	
	private List<TableEntry> tableLinks;
	private String userName;
	private String currentPageDirectory;
	private String activeLinkClass = "active";
	
	public AdminNavLinkBuilder() {
		this.tableLinks = new LinkedList<TableEntry>();
	}
	
	public void init(final String userName, final String currentPageDirectory) {
		this.userName = userName;
		this.currentPageDirectory = currentPageDirectory;
		
		this.tableLinks = this.buildLinks(this.userName, this.adminNavLinks);
	}
	
	private List<TableEntry> buildLinks(String userName, AdminNavLink[] adminNavLinks) {
		LinkedList<TableEntry> output = new LinkedList<TableEntry>();
		
		for(AdminNavLink adminNavLink : adminNavLinks) {
			TableEntry tableEntry;
			if(adminNavLink.getDisplayText().equals(logoutText)) {
				tableEntry = new TableEntry(adminNavLink.getDisplayText(), adminNavLink.getLinkToFile());
			} else {
				tableEntry = new TableEntry(adminNavLink.getDisplayText(), adminNavLink.getLinkToFile() + "/" + userName);
			}
			
			if(adminNavLink.getLinkToFile().equals(this.currentPageDirectory)) {
				tableEntry.setHtmlClass(this.activeLinkClass);
			}
			
			output.add(tableEntry);
		}
		
		return output;
	}
	
	public TableEntry[] getLinks() {
		return this.tableLinks.toArray(new TableEntry[this.tableLinks.size()]);
	}
	
}
