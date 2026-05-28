package flow.controller;

import flow.model.Achievement;
import flow.model.LevelSystem;
import flow.model.User;
import flow.service.AchievementService;
import flow.service.AuthService;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileController {
    private AchievementService achievementService;

    public ProfileController() {
        this.achievementService = new AchievementService();
    }

    public User getCurrentUser() {
        return AuthService.getCurrentUser();
    }

    public List<Achievement> getUnlockedAchievements() {
        User user = getCurrentUser();
        if (user == null) return List.of();
        return achievementService.getAchievements().stream()
                .filter(a -> a.isUnlocked(user))
                .collect(Collectors.toList());
    }

    public int getNextLevelXP() {
        User user = getCurrentUser();
        if (user == null) return 100;
        return LevelSystem.getTotalXpForLevel(user.getLevel() + 1);
    }

    public int getCurrentLevelMinXP() {
        User user = getCurrentUser();
        if (user == null) return 0;
        return LevelSystem.getTotalXpForLevel(user.getLevel());
    }
}
