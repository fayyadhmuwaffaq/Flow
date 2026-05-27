package flow.service;

import flow.database.TaskRepository;
import flow.model.Task;

import java.util.List;

public class TaskService {
    private TaskRepository taskRepository;

    public TaskService() {
        this.taskRepository = new TaskRepository();
    }

    public void addTask(Task task) {
        taskRepository.addTask(task);
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    public void deleteTask(int taskId) {
        taskRepository.deleteTask(taskId);
    }

    public List<Task> getAllTasks(int projectId) {
        return taskRepository.getAllTasks(projectId);
    }

    public List<Task> getTasksForMember(int projectId, String username) {
        return taskRepository.getTasksByMember(projectId, username);
    }

    public int getTotalTasksCount(int projectId) {
        return taskRepository.getTotalTasksCount(projectId);
    }

    public int getCompletedTasksCount(int projectId) {
        return taskRepository.getCompletedTasksCount(projectId);
    }

    public int getMemberCompletedTasksCount(int projectId, String username) {
        return taskRepository.getMemberCompletedTasksCount(projectId, username);
    }

    // Global methods for Dashboard
    public int getGlobalTotalTasksCount() {
        return taskRepository.getGlobalTotalTasksCount();
    }

    public int getGlobalCompletedTasksCount() {
        return taskRepository.getGlobalCompletedTasksCount();
    }

    public int getGlobalMemberCompletedTasksCount(String username) {
        return taskRepository.getGlobalMemberCompletedTasksCount(username);
    }
}
