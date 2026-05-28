package flow.controller;

import flow.service.AuthService;
import flow.ui.DashboardView;
import flow.ui.RegisterView;
import flow.utils.AlertHelper;
import flow.utils.SceneManager;
import flow.utils.Validator;

public class LoginController {
    private AuthService authService;

    public LoginController() {
        this.authService = new AuthService();
    }

    public void handleLogin(String username, String password) {
        if (Validator.isEmpty(username) || Validator.isEmpty(password)) {
            AlertHelper.showError("Login Error", "Username and password cannot be empty.");
            return;
        }

        if (authService.login(username, password)) {
            // Success - navigate to main layout
            SceneManager.switchScene(new flow.ui.MainLayout());
        } else {
            AlertHelper.showError("Login Failed", "Invalid username or password.");
        }
    }

    public void handleGoToRegister() {
        SceneManager.switchScene(new RegisterView());
    }
}
