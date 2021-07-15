package com.orm;
// object-relation mapping between UserCredential class in this project and the User Credential in the postgre Database(just password/security question/answer to the security question)
public class UserCredential
{
	private String id;
	private String password;
	private String security_question;
	private String security_answer;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecurity_question() {
		return security_question;
	}
	public void setSecurity_question(String security_question) {
		this.security_question = security_question;
	}
	public String getSecurity_answer() {
		return security_answer;
	}
	public void setSecurity_answer(String security_answer) {
		this.security_answer = security_answer;
	}
	
	@Override
	public String toString() {
		return "UserCredentials [id=" + id + ", password=" + password + "]";
	}
}
