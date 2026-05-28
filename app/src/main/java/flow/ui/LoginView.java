package flow.ui;

import flow.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.StackPane;

public class LoginView extends StackPane {
    private LoginController controller;

    public LoginView() {
        this.controller = new LoginController();
        initView();
    }

    private void initView() {
        setStyle("-fx-background-color: #1E1E2F;");

        VBox card = new VBox(20);
        card.setMaxWidth(400);
        card.setMaxHeight(500);
        card.setPadding(new Insets(40));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #2A2A40; -fx-background-radius: 15;");

        Label titleLabel = new Label("FLOW");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
        titleLabel.setTextFill(javafx.scene.paint.Color.web("#7B61FF"));

        Label subtitleLabel = new Label("Manage your tasks with ease");
        subtitleLabel.setTextFill(javafx.scene.paint.Color.web("#A0A0B8"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: #1E1E2F; -fx-text-fill: white; -fx-prompt-text-fill: #606080; -fx-background-radius: 5; -fx-padding: 10;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #1E1E2F; -fx-text-fill: white; -fx-prompt-text-fill: #606080; -fx-background-radius: 5; -fx-padding: 10;");

        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 5; -fx-cursor: hand;");
        loginButton.setOnAction(e -> controller.handleLogin(usernameField.getText(), passwordField.getText()));

        Hyperlink registerLink = new Hyperlink("Don't have an account? Register here");
        registerLink.setTextFill(javafx.scene.paint.Color.web("#7B61FF"));
        registerLink.setStyle("-fx-underline: false;");
        registerLink.setOnAction(e -> controller.handleGoToRegister());

        card.getChildren().addAll(titleLabel, subtitleLabel, new Label(""), usernameField, passwordField, loginButton, registerLink);
        getChildren().add(card);
    }
}
