package com.cts.authentication.model;

import javax.validation.constraints.Pattern;

public class UserCredentials {

	private String userName;
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message="Password should be alphanumeric")
	private String password;
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
}
