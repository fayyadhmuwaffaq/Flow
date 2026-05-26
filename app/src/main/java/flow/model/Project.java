package flow.model;

public class Project {
    private int id;
    private String name;
    private String description;
    private int managerId;

    public Project(int id, String name, String description, int managerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.managerId = managerId;
    }

    // ID
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }

    // Name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Manager ID
    public int getManagerId() { return managerId; }
    public void setManagerId(int managerId) { this.managerId = managerId; }
}
