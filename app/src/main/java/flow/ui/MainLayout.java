package flow.ui;

import flow.service.AuthService;
import flow.utils.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainLayout extends BorderPane {

    private VBox sidebar;
    private Button activeBtn;

    public MainLayout() {
        SceneManager.setMainLayout(this);
        initView();
    }

    private void initView() {
        setStyle("-fx-background-color: #1E1E2F;");

        // Sidebar
        sidebar = new VBox(10);
        sidebar.setPadding(new Insets(30, 20, 30, 20));
        sidebar.setStyle("-fx-background-color: #2A2A40;");
        sidebar.setPrefWidth(250);

        Label logoLabel = new Label("FLOW");
        logoLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        logoLabel.setTextFill(Color.web("#7B61FF"));
        logoLabel.setPadding(new Insets(0, 0, 30, 0));
        
        Button dashboardBtn = createSidebarButton("Dashboard");
        Button projectsBtn = createSidebarButton("Projects");
        Button profileBtn = createSidebarButton("Profile");
        Button achievementBtn = createSidebarButton("Achievement Hall");
        Button logoutBtn = createSidebarButton("Logout");
        
        dashboardBtn.setOnAction(e -> {
            setActiveButton(dashboardBtn);
            setContent("Dashboard", new DashboardView().getView());
        });
        
        projectsBtn.setOnAction(e -> {
            setActiveButton(projectsBtn);
            setContent(new ProjectListView());
        });
        
        profileBtn.setOnAction(e -> {
            setActiveButton(profileBtn);
            setContent("Profile", new ProfileView().getView());
        });
        
        achievementBtn.setOnAction(e -> {
            setActiveButton(achievementBtn);
            setContent("Achievement Hall", new AchievementView().getView());
        });
        
        logoutBtn.setOnAction(e -> {
            AuthService.logout();
            SceneManager.switchScene(new LoginView());
        });

        sidebar.getChildren().addAll(logoLabel, dashboardBtn, projectsBtn, profileBtn, achievementBtn, logoutBtn);
        setLeft(sidebar);

        // Default content
        setContent("Dashboard", new DashboardView().getView());
        setActiveButton(dashboardBtn);
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);
        
        boolean isLogout = text.equals("Logout");
        String baseColor = isLogout ? "#FF5252" : "#A0A0B8";
        String hoverBg = isLogout ? "rgba(255, 82, 82, 0.1)" : "#3A3A55";
        String hoverText = isLogout ? "#FF5252" : "white";
        String borderColor = isLogout ? "rgba(255, 82, 82, 0.3)" : "transparent";
        String hoverBorder = isLogout ? "#FF5252" : "transparent";

        btn.setStyle(String.format("-fx-background-color: transparent; -fx-text-fill: %s; -fx-font-size: 16; -fx-padding: 12; -fx-cursor: hand; -fx-background-radius: 8; -fx-border-color: %s; -fx-border-radius: 8;", baseColor, borderColor));
        
        btn.setOnMouseEntered(e -> {
            if (btn != activeBtn) {
                btn.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s; -fx-font-size: 16; -fx-padding: 12; -fx-cursor: hand; -fx-background-radius: 8; -fx-border-color: %s; -fx-border-radius: 8;", hoverBg, hoverText, hoverBorder));
            }
        });
        
        btn.setOnMouseExited(e -> {
            if (btn != activeBtn) {
                btn.setStyle(String.format("-fx-background-color: transparent; -fx-text-fill: %s; -fx-font-size: 16; -fx-padding: 12; -fx-cursor: hand; -fx-background-radius: 8; -fx-border-color: %s; -fx-border-radius: 8;", baseColor, borderColor));
            }
        });
        
        return btn;
    }

    public void highlightButton(String text) {
        for (Node node : sidebar.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                if (btn.getText().equals(text)) {
                    setActiveButton(btn);
                    break;
                }
            }
        }
    }

    private void setActiveButton(Button btn) {
        if (activeBtn != null) {
            activeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #A0A0B8; -fx-font-size: 16; -fx-padding: 12; -fx-cursor: hand; -fx-background-radius: 8;");
        }
        activeBtn = btn;
        if (activeBtn != null && !activeBtn.getText().equals("Logout")) {
            activeBtn.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 12; -fx-cursor: hand; -fx-background-radius: 8;");
        }
    }

    public void setContent(String title, Node node) {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: #1E1E2F;");
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.WHITE);
        
        container.getChildren().addAll(titleLabel, node);
        setCenter(container);
    }

    public void setContent(Node node) {
        setCenter(node);
    }
}
