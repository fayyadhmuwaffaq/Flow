package flow.model;

public class Member extends User {
    public Member(int id, String username, String password, int level, int xp) {
        super(id, username, password, "Member", level, xp);
    }
}
