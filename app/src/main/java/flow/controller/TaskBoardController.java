package flow.controller;

import flow.model.*;
import flow.service.AuthService;
import flow.service.TaskService;
import flow.service.XPService;
import java.time.LocalDate;
import java.util.List;

public class TaskBoardController {
    private TaskService taskService;
    private XPService xpService;
    private int projectId;

    public TaskBoardController(int projectId) {
        this.taskService = new TaskService();
        this.xpService = new XPService();
        this.projectId = projectId;
    }

    public List<Task> getAllTasks() {
        return taskService.getAllTasks(projectId);
    }

    public User getCurrentUser() {
        return AuthService.getCurrentUser();
    }

    public boolean isManager() {
        return getCurrentUser() instanceof Manager;
    }

    public void addTask(String title, String description, String assignedMember, LocalDate deadline) {
        if (!isManager()) return;
        Task task = new TodoTask(0, projectId, title, description, assignedMember, deadline);
        taskService.addTask(task);
    }

    public void updateTask(Task task, String newTitle, String newDescription, String newAssignedMember, LocalDate newDeadline) {
        task.setTitle(newTitle);
        task.setDescription(newDescription);
        
        if (isManager()) {
            task.setAssignedMember(newAssignedMember);
            task.setDeadline(newDeadline);
        }
        
        taskService.updateTask(task);
    }

    public void deleteTask(int taskId) {
        taskService.deleteTask(taskId);
    }

    public void moveTask(Task task, String newStatus) {
        String oldStatus = task.getStatus();
        if (oldStatus.equals(newStatus)) return;

        task.setStatus(newStatus);
        taskService.updateTask(task);

        // Update XP
        xpService.updateXP(getCurrentUser(), oldStatus, newStatus);
    }
}
