package com.ppm.demo.exceptions;

public class ProjectNotFoundResponse {
	private String projectNotFound;

	public String getProjectNotFound() {
		return projectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		this.projectNotFound = projectNotFound;
	}
	
	public ProjectNotFoundResponse(String projectNotFound) {
		super(); 
		this.projectNotFound = projectNotFound;
	}
	

}
