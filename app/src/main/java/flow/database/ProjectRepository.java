package flow.database;

import flow.model.Project;
import flow.model.User;
import flow.model.Manager;
import flow.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {
    private Connection connection;

    public ProjectRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addProject(Project p) {
        String sql = "INSERT INTO projects (name, description, manager_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getDescription());
            pstmt.setInt(3, p.getManagerId());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                p.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Project> getProjectsForManager(int managerId) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE manager_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, managerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                projects.add(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("manager_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public List<Project> getProjectsForMember(int userId) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.* FROM projects p JOIN project_members pm ON p.id = pm.project_id WHERE pm.user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                projects.add(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("manager_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public void addMemberToProject(int projectId, int userId) {
        String sql = "INSERT OR IGNORE INTO project_members (project_id, user_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMemberFromProject(int projectId, int userId) {
        String sql = "DELETE FROM project_members WHERE project_id = ? AND user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getMembersOfProject(int projectId) {
        List<User> members = new ArrayList<>();
        String sql = "SELECT u.* FROM users u JOIN project_members pm ON u.id = pm.user_id WHERE pm.project_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
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
