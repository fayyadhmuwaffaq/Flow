package flow.ui;

import flow.controller.DashboardController;
import flow.model.Achievement;
import flow.model.LevelSystem;
import flow.model.User;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DashboardView {

    private DashboardController controller;
    private ScrollPane scrollPane;

    public DashboardView() {
        this.controller = new DashboardController();
        initView();
    }

    private void initView() {
        VBox root = new VBox(30);
        root.setPadding(new Insets(10, 0, 10, 0));
        root.setStyle("-fx-background-color: #1E1E2F;");

        User user = controller.getCurrentUser();

        // Stats Cards
        HBox statsBox = new HBox(20);
        statsBox.getChildren().addAll(
            createStatCard("Total Tasks", String.valueOf(controller.getTotalTasks()), "#7B61FF"),
            createStatCard("Completed", String.valueOf(controller.getCompletedTasks()), "#00C853"),
            createStatCard("Current Level", String.valueOf(user.getLevel()) + " (" + LevelSystem.getLevelTitle(user.getLevel()) + ")", "#FFD600"),
            createStatCard("Total XP", String.valueOf(user.getXp()), "#2979FF")
        );
        HBox.setHgrow(statsBox, Priority.ALWAYS);

        // XP Progress
        VBox progressBox = createCard();
        Label progressLabel = new Label("XP Milestone Progress");
        progressLabel.setTextFill(Color.WHITE);
        progressLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        int currentXP = user.getXp();
        int minXP = controller.getCurrentLevelMinXP();
        int maxXP = controller.getNextLevelXP();
        double progress = (double)(currentXP - minXP) / (maxXP - minXP);
        
        ProgressBar pb = new ProgressBar(progress);
        pb.setMaxWidth(Double.MAX_VALUE);
        pb.setPrefHeight(20);
        pb.setStyle("-fx-accent: #7B61FF;");
        
        Label xpDetails = new Label(currentXP + " / " + maxXP + " XP to Level " + (user.getLevel() + 1));
        xpDetails.setTextFill(Color.web("#A0A0B8"));
        
        progressBox.getChildren().addAll(progressLabel, pb, xpDetails);

        // Achievement Preview
        VBox achievementBox = createCard();
        Label achievementLabel = new Label("Achievements Preview");
        achievementLabel.setTextFill(Color.WHITE);
        achievementLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        HBox badgeBox = new HBox(15);
        badgeBox.setPadding(new Insets(10, 0, 0, 0));
        
        List<Achievement> unlocked = controller.getUnlockedAchievements();
        if (unlocked.isEmpty()) {
            Label noAch = new Label("No achievements unlocked yet. Keep working!");
            noAch.setTextFill(Color.web("#A0A0B8"));
            badgeBox.getChildren().add(noAch);
        } else {
            for (Achievement a : unlocked) {
                Label badge = new Label(a.getName());
                badge.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-padding: 5 10 5 10; -fx-background-radius: 20; -fx-font-weight: bold;");
                badgeBox.getChildren().add(badge);
            }
        }
        
        achievementBox.getChildren().addAll(achievementLabel, badgeBox);

        // Leaderboard
        VBox leaderboardBox = createCard();
        Label leaderboardLabel = new Label("Leaderboard");
        leaderboardLabel.setTextFill(Color.WHITE);
        leaderboardLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 0, 0, 0));
        
        String[] headers = {"User", "Level", "XP", "Done"};
        for (int i = 0; i < headers.length; i++) {
            Label h = new Label(headers[i]);
            h.setTextFill(Color.web("#7B61FF"));
            h.setFont(Font.font("System", FontWeight.BOLD, 14));
            grid.add(h, i, 0);
        }
        
        int row = 1;
        for (User u : controller.getLeaderboard()) {
            grid.add(createTableCell(u.getUsername()), 0, row);
            grid.add(createTableCell(String.valueOf(u.getLevel())), 1, row);
            grid.add(createTableCell(String.valueOf(u.getXp())), 2, row);
            grid.add(createTableCell(String.valueOf(controller.getCompletedTasksForUser(u.getUsername()))), 3, row);
            row++;
            if (row > 10) break; // Top 10
        }
        
        leaderboardBox.getChildren().addAll(leaderboardLabel, grid);

        root.getChildren().addAll(statsBox, progressBox, achievementBox, leaderboardBox);
        
        scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #1E1E2F; -fx-background-color: #1E1E2F; -fx-border-color: #1E1E2F;");
    }

    private VBox createStatCard(String title, String value, String color) {
        VBox card = createCard();
        card.setPrefWidth(200);
        HBox.setHgrow(card, Priority.ALWAYS);
        
        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.web("#A0A0B8"));
        titleLabel.setFont(Font.font(14));
        
        Label valueLabel = new Label(value);
        valueLabel.setTextFill(Color.web(color));
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        
        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private VBox createCard() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #2A2A40; -fx-background-radius: 15;");
        return card;
    }

    private Label createTableCell(String text) {
        Label l = new Label(text);
        l.setTextFill(Color.WHITE);
        return l;
    }

    public Node getView() {
        return scrollPane;
    }
}
