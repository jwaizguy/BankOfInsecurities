package com.controller.models;

public class PasswordChange {
	private String username;
    private String password;
    
    public void setUsername(String username) {
    	this.username = username;
    }
    
    public String getUsername() {
    	return this.username;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    @Override
    public String toString() {
    	return this.username + " " + this.password;
    }
}
