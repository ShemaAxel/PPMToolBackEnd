package com.ppm.demo.exceptions;

public class ProjectTaskNotFoundExcResponse {

	private String taskNotFound;

	public ProjectTaskNotFoundExcResponse(String taskSequence) {
		super(); 
		this.taskNotFound = taskSequence;
	}
	
	public String getTaskNotFound() {
		return taskNotFound;
	}

	public void setTaskNotFound(String taskNotFound) {
		this.taskNotFound = taskNotFound;
	}
	
	
}
