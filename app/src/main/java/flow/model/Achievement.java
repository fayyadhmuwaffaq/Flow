package flow.model;

/**
 * Abstract class for achievements in the Flow application.
 */
public abstract class Achievement {
    private String name;
    private String description;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    /**
     * Abstract method to check if the achievement is unlocked.
     * @param user The user to check against.
     * @return true if unlocked, false otherwise.
     */
    public abstract boolean isUnlocked(User user);
}
