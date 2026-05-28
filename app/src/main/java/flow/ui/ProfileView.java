package flow.ui;

import flow.controller.ProfileController;
import flow.model.Achievement;
import flow.model.LevelSystem;
import flow.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;

public class ProfileView {

    private ProfileController controller;
    private VBox root;

    public ProfileView() {
        this.controller = new ProfileController();
        initView();
    }

    private void initView() {
        root = new VBox(30);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #1E1E2F;");

        User user = controller.getCurrentUser();

        VBox profileCard = new VBox(20);
        profileCard.setPadding(new Insets(40));
        profileCard.setStyle("-fx-background-color: #2A2A40; -fx-background-radius: 20;");
        profileCard.setMaxWidth(600);
        profileCard.setAlignment(Pos.CENTER);

        // Avatar Placeholder
        Label avatar = new Label(user.getUsername().substring(0, 1).toUpperCase());
        avatar.setPrefSize(100, 100);
        avatar.setAlignment(Pos.CENTER);
        avatar.setStyle("-fx-background-color: #7B61FF; -fx-background-radius: 50; -fx-text-fill: white; -fx-font-size: 40; -fx-font-weight: bold;");

        Label nameLabel = new Label(user.getUsername());
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 32));

        Label roleLabel = new Label(user.getClass().getSimpleName());
        roleLabel.setTextFill(Color.web("#7B61FF"));
        roleLabel.setFont(Font.font(18));

        VBox statsBox = new VBox(15);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setMaxWidth(400);

        statsBox.getChildren().addAll(
            createProfileStat("Current Level", user.getLevel() + " (" + LevelSystem.getLevelTitle(user.getLevel()) + ")"),
            createProfileStat("Total XP", String.valueOf(user.getXp()))
        );

        // XP Progress
        VBox progressBox = new VBox(10);
        progressBox.setAlignment(Pos.CENTER);
        
        int currentXP = user.getXp();
        int minXP = controller.getCurrentLevelMinXP();
        int maxXP = controller.getNextLevelXP();
        double progress = (double)(currentXP - minXP) / (maxXP - minXP);
        
        ProgressBar pb = new ProgressBar(progress);
        pb.setPrefWidth(400);
        pb.setPrefHeight(15);
        pb.setStyle("-fx-accent: #7B61FF;");
        
        Label xpDetails = new Label(currentXP + " / " + maxXP + " XP to next level");
        xpDetails.setTextFill(Color.web("#A0A0B8"));

        progressBox.getChildren().addAll(pb, xpDetails);

        profileCard.getChildren().addAll(avatar, nameLabel, roleLabel, statsBox, progressBox);

        // Achievements Section
        VBox achievementBox = new VBox(15);
        achievementBox.setAlignment(Pos.CENTER);
        achievementBox.setPadding(new Insets(20));
        achievementBox.setMaxWidth(600);
        achievementBox.setStyle("-fx-background-color: #2A2A40; -fx-background-radius: 20;");

        Label achievementTitle = new Label("Unlocked Achievements");
        achievementTitle.setTextFill(Color.WHITE);
        achievementTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        FlowPane achievementFlow = new FlowPane(10, 10);
        achievementFlow.setAlignment(Pos.CENTER);

        List<Achievement> unlocked = controller.getUnlockedAchievements();
        if (unlocked.isEmpty()) {
            Label noneLabel = new Label("No achievements unlocked yet.");
            noneLabel.setTextFill(Color.web("#A0A0B8"));
            achievementFlow.getChildren().add(noneLabel);
        } else {
            for (Achievement a : unlocked) {
                Label badge = new Label(a.getName());
                badge.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-padding: 5 15; -fx-background-radius: 15; -fx-font-weight: bold;");
                achievementFlow.getChildren().add(badge);
            }
        }

        achievementBox.getChildren().addAll(achievementTitle, achievementFlow);

        root.getChildren().addAll(profileCard, achievementBox);
    }

    private VBox createProfileStat(String label, String value) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        
        Label l = new Label(label);
        l.setTextFill(Color.web("#A0A0B8"));
        l.setFont(Font.font(14));
        
        Label v = new Label(value);
        v.setTextFill(Color.WHITE);
        v.setFont(Font.font("System", FontWeight.BOLD, 22));
        
        box.getChildren().addAll(l, v);
        return box;
    }

    public Node getView() {
        return root;
    }
}
