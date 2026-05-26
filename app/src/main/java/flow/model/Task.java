package flow.model;

import java.time.LocalDate;

/**
 * Abstract base class for all tasks in the Flow application.
 */
public abstract class Task {
    private int id;
    private int projectId;
    private String title;
    private String description;
    private String status;
    private String assignedMember;
    private LocalDate deadline;

    public Task(int id, int projectId, String title, String description, String status, String assignedMember, LocalDate deadline) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedMember = assignedMember;
        this.deadline = deadline;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAssignedMember() { return assignedMember; }
    public void setAssignedMember(String assignedMember) { this.assignedMember = assignedMember; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
}
