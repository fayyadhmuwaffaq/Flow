package flow.service;

import flow.database.UserRepository;
import flow.model.LevelSystem;
import flow.model.User;

public class XPService {
    private UserRepository userRepository;

    public XPService() {
        this.userRepository = new UserRepository();
    }

    public void updateXP(User user, String oldStatus, String newStatus) {
        int xpChange = 0;

        if ("On Progress".equalsIgnoreCase(oldStatus) && "Done".equalsIgnoreCase(newStatus)) {
            xpChange = 20;
        } else if ("Done".equalsIgnoreCase(oldStatus) && ("On Progress".equalsIgnoreCase(newStatus) || "To-Do".equalsIgnoreCase(newStatus))) {
            xpChange = -20;
        }

        if (xpChange != 0) {
            int newXP = Math.max(0, user.getXp() + xpChange);
            user.setXp(newXP);
            
            int newLevel = LevelSystem.calculateLevel(newXP);
            user.setLevel(newLevel);
            
            userRepository.updateXPAndLevel(user.getId(), newXP, newLevel);
        }
    }
}
