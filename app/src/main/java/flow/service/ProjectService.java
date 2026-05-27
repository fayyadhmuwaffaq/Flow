package flow.service;

import flow.database.ProjectRepository;
import flow.database.UserRepository;
import flow.model.Project;
import flow.model.User;
import java.util.List;

public class ProjectService {
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    public ProjectService() {
        this.projectRepository = new ProjectRepository();
        this.userRepository = new UserRepository();
    }

    public void createProject(String name, String description, int managerId) {
        Project project = new Project(0, name, description, managerId);
        projectRepository.addProject(project);
    }

    public List<Project> getProjectsForUser(User user) {
        if ("Manager".equalsIgnoreCase(user.getRole())) {
            return projectRepository.getProjectsForManager(user.getId());
        } else {
            return projectRepository.getProjectsForMember(user.getId());
        }
    }

    public List<User> getAllMembers() {
        return userRepository.getAllMembers();
    }

    public List<User> getProjectMembers(int projectId) {
        return projectRepository.getMembersOfProject(projectId);
    }

    public void addMemberToProject(int projectId, int userId) {
        projectRepository.addMemberToProject(projectId, userId);
    }

    public void removeMemberFromProject(int projectId, int userId) {
        projectRepository.removeMemberFromProject(projectId, userId);
    }
}
