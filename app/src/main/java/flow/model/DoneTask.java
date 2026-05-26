package flow.model;

import java.time.LocalDate;

public class DoneTask extends Task {
    public DoneTask(int id, int projectId, String title, String description, String assignedMember, LocalDate deadline) {
        super(id, projectId, title, description, "Done", assignedMember, deadline);
    }
}
