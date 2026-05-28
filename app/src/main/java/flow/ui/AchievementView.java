package flow.ui;

import flow.controller.AchievementController;
import flow.model.Achievement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class AchievementView {

    private AchievementController controller;
    private ScrollPane scrollPane;

    public AchievementView() {
        this.controller = new AchievementController();
        initView();
    }

    private void initView() {
        VBox root = new VBox(30);
        root.setPadding(new Insets(10, 0, 10, 0));
        root.setStyle("-fx-background-color: #1E1E2F;");

        Label subtitleLabel = new Label("Selesaikan tantangan untuk membuka pencapaian baru");
        subtitleLabel.setTextFill(Color.web("#A0A0B8"));
        subtitleLabel.setFont(Font.font(16));

        TilePane tilePane = new TilePane();
        tilePane.setHgap(20);
        tilePane.setVgap(20);
        tilePane.setPrefColumns(3);
        tilePane.setAlignment(Pos.TOP_LEFT);

        List<Achievement> achievements = controller.getAchievements();
        for (Achievement achievement : achievements) {
            boolean isUnlocked = controller.isUnlocked(achievement);
            tilePane.getChildren().add(createAchievementCard(achievement, isUnlocked));
        }

        root.getChildren().addAll(subtitleLabel, tilePane);

        scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #1E1E2F; -fx-background-color: #1E1E2F; -fx-border-color: #1E1E2F;");
    }

    private VBox createAchievementCard(Achievement achievement, boolean isUnlocked) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setPrefSize(250, 200);
        card.setAlignment(Pos.CENTER);
        
        String baseStyle = "-fx-background-radius: 20; -fx-background-color: #2A2A40;";
        if (isUnlocked) {
            card.setStyle(baseStyle + " -fx-border-color: #7B61FF; -fx-border-width: 2; -fx-border-radius: 20;");
        } else {
            card.setStyle(baseStyle);
            card.setOpacity(0.5);
        }

        Label iconLabel = new Label(isUnlocked ? "🏆" : "🔒");
        iconLabel.setFont(Font.font(40));
        
        Label nameLabel = new Label(achievement.getName());
        nameLabel.setTextFill(isUnlocked ? Color.WHITE : Color.web("#A0A0B8"));
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setWrapText(true);

        Label descLabel = new Label(achievement.getDescription());
        descLabel.setTextFill(Color.web("#A0A0B8"));
        descLabel.setFont(Font.font(14));
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setWrapText(true);

        card.getChildren().addAll(iconLabel, nameLabel, descLabel);
        return card;
    }

    public Node getView() {
        return scrollPane;
    }
}
