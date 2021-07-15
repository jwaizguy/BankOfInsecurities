package com.orm;
// object-relation mapping between Role class and the postgre database Role table
public class Role
{
	private char roleID;
	private String roleDescription;
	
	public char getRoleID() {
		return roleID;
	}
	public void setRoleID(char roleID) {
		this.roleID = roleID;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
}
