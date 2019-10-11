package com.ppm.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppm.demo.domain.Backlog;
import com.ppm.demo.domain.Project;
import com.ppm.demo.exceptions.ProjectIdException;
import com.ppm.demo.repositories.BacklogRepository;
import com.ppm.demo.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;
	public Project saveOrUpdateProject(Project project) {
		// logic
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			//create ,backlog when project created
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				//settin relationship
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase()); 
				backlog.setProject(project);
				project.setBacklog(backlog);
			}
			//update we dont lose backlog details
			if(project.getId()!=null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID '" + project.getProjectIdentifier().toUpperCase() + "' aready exists");
		}

	}

	public Project findProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if (project == null)
			throw new ProjectIdException("Project ID' " + projectId.toUpperCase() + "' does not exists");

		return project;
	}

	public Iterable<Project> findAll() {
		return projectRepository.findAll();
	}

	public void deleteProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if (project == null)
			throw new ProjectIdException("Project ID' " + projectId.toUpperCase() + "' does not exists");

		projectRepository.delete(project);
	}
	
	

}
