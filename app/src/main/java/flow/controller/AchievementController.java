package flow.controller;

import flow.model.Achievement;
import flow.model.User;
import flow.service.AchievementService;
import flow.service.AuthService;
import java.util.List;

public class AchievementController {
    private AchievementService achievementService;

    public AchievementController() {
        this.achievementService = new AchievementService();
    }

    public List<Achievement> getAchievements() {
        return achievementService.getAchievements();
    }

    public boolean isUnlocked(Achievement achievement) {
        User currentUser = AuthService.getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        return achievement.isUnlocked(currentUser);
    }
}
