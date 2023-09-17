package projects.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import projects.doa.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;

public class ProjectService { // acts as a pass-through between the main application file and the DAO file in the data layer.
	
	
	private ProjectDao projectDao = new ProjectDao();

	public Project addProject(Project project) {
		return projectDao.insertProject(project);
	}

	public List<Project> fetchAllProjects() { // returns projects ordered by the ID
		// formatter:off
		return projectDao.fetchAllProjects()
				.stream() // returns a stream as the collection's source
				.sorted((p1, p2) -> p1.getProjectId() - p2.getProjectId()) // sorts the collection with a lambda expression
				.collect(Collectors.toList()); // Collectors has a bunch of convenience methods and the .toList() method will return a collector that accumulates the input elements into a new List
		// formatter:on
	}

	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectById(projectId).orElseThrow(
				() -> new NoSuchElementException(
				"Project with project ID=" + projectId
					+ " does not exist."));
	}

	public void modifyProjectDetails(Project project) {
		if(!projectDao.modifyProjectDetails(project)) { // returns a boolean that indicates whether the UPDATE operation was successful
			throw new DbException("Project with ID="
					+ project.getProjectId() + " does not exist");
		}
	}

	public void deleteProject(Integer projectId) {
		if(!projectDao.deleteProject(projectId)) {
			throw new DbException("Project with ID=" + projectId + " does not exist.");
		}
	}

}
