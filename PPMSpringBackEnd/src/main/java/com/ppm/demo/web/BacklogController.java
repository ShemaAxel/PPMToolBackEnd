package com.ppm.demo.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppm.demo.domain.ProjectTask;
import com.ppm.demo.exceptions.ProjectTaskNotFound;
import com.ppm.demo.services.MapValidationErrorService;
import com.ppm.demo.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

	}

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id) {

		return projectTaskService.findBacklogById(backlog_id);

	}

	@GetMapping("/{backlog_id}/{sequenceID}")
	public ResponseEntity<?> getProjectTaskBySequeneOrder(@PathVariable String sequenceID,
			@PathVariable String backlog_id) {
		ProjectTask projectTask = projectTaskService.findBacklogByProjectSequence(backlog_id.toUpperCase(),
				sequenceID.toUpperCase());

		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);

	}

	@PatchMapping("/{backlog_id}/{sequenceID}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id, @PathVariable String sequenceID) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		ProjectTask projectTask1 = projectTaskService.updateByProjectSequence(projectTask, backlog_id, sequenceID);
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.OK);

	}

	@DeleteMapping("/{backlog_id}/{sequenceID}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String sequenceID) {
		projectTaskService.deleteProjectTask(backlog_id, sequenceID);
		return new ResponseEntity<String>("ProjectTask with '" + sequenceID + "' was delete successfully.",
				HttpStatus.OK);
	}

}