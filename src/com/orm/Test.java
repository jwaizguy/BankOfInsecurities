package com.orm;

public class Test
{
	private String id;
	private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String name) {
		this.password = name;
	}
	@Override
	public String toString() {
		return "Test [id=" + id + ", password=" + password + "]";
	}
}
