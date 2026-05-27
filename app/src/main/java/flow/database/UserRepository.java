package flow.database;

import flow.model.Manager;
import flow.model.Member;
import flow.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private Connection connection;

    public UserRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean register(String username, String password, String role) {
        String sql = "INSERT INTO users (username, password, role, level, xp) VALUES (?, ?, ?, 1, 0)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateXPAndLevel(int userId, int xp, int level) {
        String sql = "UPDATE users SET xp = ?, level = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, xp);
            pstmt.setInt(2, level);
            pstmt.setInt(3, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getLeaderboard() {
        List<User> leaderboard = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY xp DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                leaderboard.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    public List<User> getAllMembers() {
        List<User> members = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'Member'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                members.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String role = rs.getString("role");
        int level = rs.getInt("level");
        int xp = rs.getInt("xp");

        if ("Manager".equalsIgnoreCase(role)) {
            return new Manager(id, username, password, level, xp);
        } else {
            return new Member(id, username, password, level, xp);
        }
    }
}
