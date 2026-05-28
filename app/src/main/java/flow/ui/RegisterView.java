package flow.ui;

import flow.controller.RegisterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.StackPane;

public class RegisterView extends StackPane {
    private RegisterController controller;

    public RegisterView() {
        this.controller = new RegisterController();
        initView();
    }

    private void initView() {
        setStyle("-fx-background-color: #1E1E2F;");

        VBox card = new VBox(15);
        card.setMaxWidth(400);
        card.setMaxHeight(600); // Slightly taller than login to accommodate extra fields
        card.setPadding(new Insets(40));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #2A2A40; -fx-background-radius: 15;");

        Label titleLabel = new Label("REGISTER");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.setTextFill(javafx.scene.paint.Color.web("#7B61FF"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: #1E1E2F; -fx-text-fill: white; -fx-prompt-text-fill: #606080; -fx-background-radius: 5; -fx-padding: 10;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #1E1E2F; -fx-text-fill: white; -fx-prompt-text-fill: #606080; -fx-background-radius: 5; -fx-padding: 10;");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setStyle("-fx-background-color: #1E1E2F; -fx-text-fill: white; -fx-prompt-text-fill: #606080; -fx-background-radius: 5; -fx-padding: 10;");

        Label roleLabel = new Label("Select Role:");
        roleLabel.setTextFill(javafx.scene.paint.Color.web("#A0A0B8"));
        
        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton managerRadio = new RadioButton("Manager");
        managerRadio.setToggleGroup(roleGroup);
        managerRadio.setTextFill(javafx.scene.paint.Color.WHITE);
        managerRadio.setSelected(true);

        RadioButton memberRadio = new RadioButton("Member");
        memberRadio.setToggleGroup(roleGroup);
        memberRadio.setTextFill(javafx.scene.paint.Color.WHITE);

        HBox roleBox = new HBox(20, managerRadio, memberRadio);
        roleBox.setAlignment(Pos.CENTER);

        Button registerButton = new Button("Register");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        registerButton.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 5; -fx-cursor: hand;");
        registerButton.setOnAction(e -> {
            String role = ((RadioButton) roleGroup.getSelectedToggle()).getText();
            controller.handleRegister(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText(), role);
        });

        Hyperlink loginLink = new Hyperlink("Already have an account? Login here");
        loginLink.setTextFill(javafx.scene.paint.Color.web("#7B61FF"));
        loginLink.setStyle("-fx-underline: false;");
        loginLink.setOnAction(e -> controller.handleGoToLogin());

        card.getChildren().addAll(titleLabel, new Label(""), usernameField, passwordField, confirmPasswordField, roleLabel, roleBox, new Label(""), registerButton, loginLink);
        getChildren().add(card);
    }
}
