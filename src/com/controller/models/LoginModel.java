package com.controller.models;

public class LoginModel {
    private String userName;
    private String password;
    
    public LoginModel() { }
    
    public LoginModel(String userName, String password) {
    	this.userName = userName;
    	this.password = password;
    }
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    @Override
    public String toString() {
        return "LoginModel{" + "userName=" + userName + ", password=" + password + '}';
    }
    
    @Override
    public boolean equals(Object other){
        boolean result;
        if((other == null) || (getClass() != other.getClass())){
            result = false;
        } else{
            LoginModel otherPeople = (LoginModel)other;
            result = this.userName.equals(otherPeople.getUserName()) &&  this.password.equals(otherPeople.getPassword());
        }

        return result;
    }
}
