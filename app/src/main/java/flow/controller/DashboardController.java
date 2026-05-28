package flow.controller;

import flow.database.UserRepository;
import flow.model.Achievement;
import flow.model.LevelSystem;
import flow.model.User;
import flow.service.AchievementService;
import flow.service.AuthService;
import flow.service.TaskService;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {
    private TaskService taskService;
    private UserRepository userRepository;
    private AchievementService achievementService;

    public DashboardController() {
        this.taskService = new TaskService();
        this.userRepository = new UserRepository();
        this.achievementService = new AchievementService();
    }

    public List<Achievement> getUnlockedAchievements() {
        User user = getCurrentUser();
        if (user == null) return List.of();
        return achievementService.getAchievements().stream()
                .filter(a -> a.isUnlocked(user))
                .collect(Collectors.toList());
    }

    public int getTotalTasks() {
        return taskService.getGlobalTotalTasksCount();
    }

    public int getCompletedTasks() {
        return taskService.getGlobalCompletedTasksCount();
    }

    public User getCurrentUser() {
        return AuthService.getCurrentUser();
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

    public List<User> getLeaderboard() {
        return userRepository.getLeaderboard();
    }
    
    public int getCompletedTasksForUser(String username) {
        return taskService.getGlobalMemberCompletedTasksCount(username);
    }
}
