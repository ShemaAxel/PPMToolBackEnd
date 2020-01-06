package com.ppm.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.ppm.demo.domain.Backlog;
import com.ppm.demo.domain.Project;
import com.ppm.demo.domain.ProjectTask;
import com.ppm.demo.exceptions.ProjectIdException;
import com.ppm.demo.exceptions.ProjectNotFoundException;
import com.ppm.demo.exceptions.ProjectTaskNotFound;
import com.ppm.demo.repositories.BacklogRepository;
import com.ppm.demo.repositories.ProjectRepository;
import com.ppm.demo.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		try {
			// find the projects backLog
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			// assing
			projectTask.setBacklog(backlog);
			// sequence tracking
			Integer BacklogSequence = backlog.getPTSequence();
			// updateSequence
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);
			// ad sequence to project task
			projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			// set priority
			if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			}
			// set status
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			// save Task
			return projectTaskRepository.save(projectTask);
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project not found.");
		}

	}

	public Iterable<ProjectTask> findBacklogById(String id) {

		Project project = projectRepository.findByProjectIdentifier(id);

		if (project == null) {
			throw new ProjectNotFoundException("Project with ID: '" + id + "' not found.");
		}

		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}

	public ProjectTask findBacklogByProjectSequence(String backlodId, String id) {
		// if backlog_id exist
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlodId.toUpperCase());
		if (backlog == null)
			throw new ProjectNotFoundException("Project with ID: '" + backlodId + "' not found.");

		// projectTaskExist

		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(id);
		if (projectTask == null)
			throw new ProjectTaskNotFound("ProjectTask with ID: '" + id.toUpperCase() + "' does not exist.");

		// Check if the task projectIdentifier matches the passed projectId
		if (!projectTask.getProjectIdentifier().equals(backlodId))
			throw new ProjectTaskNotFound(
					"ProjectTask ID: '" + id.toUpperCase() + "' does not exist in project " + "ID '" + backlodId);

		return projectTask;

	}

	public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id, String sequenceId) {

		ProjectTask projectTask = findBacklogByProjectSequence(backlog_id, sequenceId);

		projectTask = updatedProjectTask;

		return projectTaskRepository.save(projectTask);
	}

	public void deleteProjectTask(String backlog_id, String sequenceId) {

		ProjectTask projectTask = findBacklogByProjectSequence(backlog_id, sequenceId);

		projectTaskRepository.delete(projectTask);

	}

}