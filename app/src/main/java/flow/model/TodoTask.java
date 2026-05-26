package flow.model;

import java.time.LocalDate;

public class TodoTask extends Task {
    public TodoTask(int id, int projectId, String title, String description, String assignedMember, LocalDate deadline) {
        super(id, projectId, title, description, "To-Do", assignedMember, deadline);
    }
}
