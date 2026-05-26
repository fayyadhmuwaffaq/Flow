package flow.model;

public class Manager extends User {
    public Manager(int id, String username, String password, int level, int xp) {
        super(id, username, password, "Manager", level, xp);
    }
}
