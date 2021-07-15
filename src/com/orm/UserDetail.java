package com.orm;
// object-relation mapping between user detail class here and the user detail in the postgre database
public class UserDetail
{
	private String userID;
	private String fName;
	private String lName;
	private String ssn;
	private String emailID;
	private String contact;
	private String address;
	private char roleID;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public char getRoleID() {
		return roleID;
	}
	public void setRoleID(char roleID) {
		this.roleID = roleID;
	}
}
