package com.ppm.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppm.demo.domain.Project;
import com.ppm.demo.exceptions.ProjectIdException;
import com.ppm.demo.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdateProject(Project project) {
		// logic
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
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
