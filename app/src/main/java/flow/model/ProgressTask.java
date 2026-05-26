package flow.model;

import java.time.LocalDate;

public class ProgressTask extends Task {
    public ProgressTask(int id, int projectId, String title, String description, String assignedMember, LocalDate deadline) {
        super(id, projectId, title, description, "On Progress", assignedMember, deadline);
    }
}
