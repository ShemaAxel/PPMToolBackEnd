package com.ppm.demo.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppm.demo.domain.Project;
import com.ppm.demo.services.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController { 
	@Autowired
	private ProjectService projectService;
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,BindingResult result){
		//bindResult analyze the object
		//? for a wide range of return options
		 if(result.hasErrors()) {
			 return new ResponseEntity<List<FieldError>>(result.getFieldErrors(),HttpStatus.BAD_REQUEST);
		 }
		Project project1 = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(project,HttpStatus.CREATED);
	}
}
