package flow.service;

import flow.database.UserRepository;
import flow.model.User;

public class AuthService {
    private static User currentUser;
    private UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    public boolean register(String username, String password, String role) {
        return userRepository.register(username, password, role);
    }

    public boolean login(String username, String password) {
        User user = userRepository.login(username, password);
        if (user != null) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}
