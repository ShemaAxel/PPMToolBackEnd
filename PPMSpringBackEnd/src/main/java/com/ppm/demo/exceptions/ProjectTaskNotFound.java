package com.ppm.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectTaskNotFound extends RuntimeException  {
	public ProjectTaskNotFound(String message) {
		super(message);
	}
}
