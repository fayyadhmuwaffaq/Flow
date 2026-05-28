package flow.controller;

import flow.model.Project;
import flow.model.User;
import flow.service.AuthService;
import flow.service.ProjectService;
import java.util.List;

public class ProjectController {
    private ProjectService projectService;

    public ProjectController() {
        this.projectService = new ProjectService();
    }

    public List<Project> getProjects() {
        User user = AuthService.getCurrentUser();
        if (user == null) return List.of();
        return projectService.getProjectsForUser(user);
    }

    public void createProject(String name, String description) {
        User user = AuthService.getCurrentUser();
        if (user != null && "Manager".equalsIgnoreCase(user.getRole())) {
            projectService.createProject(name, description, user.getId());
        }
    }

    public List<User> getAllMembers() {
        return projectService.getAllMembers();
    }

    public List<User> getProjectMembers(int projectId) {
        return projectService.getProjectMembers(projectId);
    }

    public void addMemberToProject(int projectId, int userId) {
        projectService.addMemberToProject(projectId, userId);
    }

    public void removeMemberFromProject(int projectId, int userId) {
        projectService.removeMemberFromProject(projectId, userId);
    }

    public User getCurrentUser() {
        return AuthService.getCurrentUser();
    }
}
