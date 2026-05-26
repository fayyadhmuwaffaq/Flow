package flow.model;

/**
 * Utility class for handling level and XP calculations.
 */
public class LevelSystem {

    /**
     * Calculates the total XP required to reach a specific level.
     * Formula: TotalXP(L) = Sum from i=1 to L-1 of (i * 100)
     */
    public static int getTotalXpForLevel(int level) {
        if (level <= 1) return 0;
        int total = 0;
        for (int i = 1; i < level; i++) {
            total += i * 100;
        }
        return total;
    }

    /**
     * Determines the level based on total XP.
     */
    public static int calculateLevel(int xp) {
        int level = 1;
        while (xp >= getTotalXpForLevel(level + 1)) {
            level++;
        }
        return level;
    }

    /**
     * Gets the achievement title based on level.
     */
    public static String getLevelTitle(int level) {
        if (level >= 5) return "Productivity King";
        if (level == 4) return "Master";
        if (level == 3) return "Deadline Hunter";
        if (level == 2) return "Rajin";
        return "Pemalas";
    }
}
