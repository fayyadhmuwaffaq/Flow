package flow.controller;

import flow.service.AuthService;
import flow.ui.LoginView;
import flow.utils.AlertHelper;
import flow.utils.SceneManager;
import flow.utils.Validator;

public class RegisterController {
    private AuthService authService;

    public RegisterController() {
        this.authService = new AuthService();
    }

    public void handleRegister(String username, String password, String confirmPassword, String role) {
        if (Validator.isEmpty(username) || Validator.isEmpty(password) || Validator.isEmpty(role)) {
            AlertHelper.showError("Registration Error", "All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            AlertHelper.showError("Registration Error", "Passwords do not match.");
            return;
        }

        if (password.length() < 6) {
            AlertHelper.showError("Registration Error", "Password must be at least 6 characters.");
            return;
        }

        if (authService.register(username, password, role)) {
            AlertHelper.showInfo("Registration Success", "Account created successfully. Please login.");
            SceneManager.switchScene(new LoginView());
        } else {
            AlertHelper.showError("Registration Failed", "Username might already exist.");
        }
    }

    public void handleGoToLogin() {
        SceneManager.switchScene(new LoginView());
    }
}
