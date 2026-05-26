package flow.model;

/**
 * Achievement based on user level.
 */
public class LevelAchievement extends Achievement {
    private int requiredLevel;

    public LevelAchievement(String name, String description, int requiredLevel) {
        super(name, description);
        this.requiredLevel = requiredLevel;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    @Override
    public boolean isUnlocked(User user) {
        return user.getLevel() >= requiredLevel;
    }
}
