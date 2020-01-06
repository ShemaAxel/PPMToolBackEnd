package com.ppm.demo.exceptions;

public class UserNotFoundResponse {

	private String user;


	public UserNotFoundResponse(String user) {
		super(); 
		this.user = user;
	}
	

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
