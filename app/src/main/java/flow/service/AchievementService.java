package flow.service;

import flow.model.Achievement;
import flow.model.LevelAchievement;
import java.util.ArrayList;
import java.util.List;

public class AchievementService {
    private List<Achievement> achievements;

    public AchievementService() {
        achievements = new ArrayList<>();
        achievements.add(new LevelAchievement("Pemalas", "Tingkatkan level ke 1", 1));
        achievements.add(new LevelAchievement("Rajin", "Tingkatkan level ke 2", 2));
        achievements.add(new LevelAchievement("Deadline Hunter", "Tingkatkan level ke 3", 3));
        achievements.add(new LevelAchievement("Master", "Tingkatkan level ke 4", 4));
        achievements.add(new LevelAchievement("Productivity King", "Tingkatkan level ke 5", 5));
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }
}
