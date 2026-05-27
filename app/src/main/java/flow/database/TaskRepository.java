package flow.database;

import flow.model.DoneTask;
import flow.model.ProgressTask;
import flow.model.Task;
import flow.model.TodoTask;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private Connection connection;

    public TaskRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addTask(Task task) {
        String sql = "INSERT INTO tasks (project_id, title, description, status, assigned_member, deadline) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, task.getProjectId());
            pstmt.setString(2, task.getTitle());
            pstmt.setString(3, task.getDescription());
            pstmt.setString(4, task.getStatus());
            pstmt.setString(5, task.getAssignedMember());
            pstmt.setString(6, task.getDeadline() != null ? task.getDeadline().toString() : null);
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                task.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET project_id = ?, title = ?, description = ?, status = ?, assigned_member = ?, deadline = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, task.getProjectId());
            pstmt.setString(2, task.getTitle());
            pstmt.setString(3, task.getDescription());
            pstmt.setString(4, task.getStatus());
            pstmt.setString(5, task.getAssignedMember());
            pstmt.setString(6, task.getDeadline() != null ? task.getDeadline().toString() : null);
            pstmt.setInt(7, task.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks(int projectId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE project_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.add(mapTask(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getTasksByMember(int projectId, String username) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE project_id = ? AND assigned_member = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.setString(2, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.add(mapTask(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public int getTotalTasksCount(int projectId) {
        String sql = "SELECT COUNT(*) FROM tasks WHERE project_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getCompletedTasksCount(int projectId) {
        String sql = "SELECT COUNT(*) FROM tasks WHERE status = 'Done' AND project_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMemberCompletedTasksCount(int projectId, String username) {
        String sql = "SELECT COUNT(*) FROM tasks WHERE status = 'Done' AND assigned_member = ? AND project_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, projectId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getGlobalTotalTasksCount() {
        String sql = "SELECT COUNT(*) FROM tasks";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getGlobalCompletedTasksCount() {
        String sql = "SELECT COUNT(*) FROM tasks WHERE status = 'Done'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getGlobalMemberCompletedTasksCount(String username) {
        String sql = "SELECT COUNT(*) FROM tasks WHERE status = 'Done' AND assigned_member = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Task mapTask(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int projectId = rs.getInt("project_id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        String status = rs.getString("status");
        String assignedMember = rs.getString("assigned_member");
        String deadlineStr = rs.getString("deadline");
        LocalDate deadline = (deadlineStr != null) ? LocalDate.parse(deadlineStr) : null;

        if ("Done".equalsIgnoreCase(status)) {
            return new DoneTask(id, projectId, title, description, assignedMember, deadline);
        } else if ("On Progress".equalsIgnoreCase(status)) {
            return new ProgressTask(id, projectId, title, description, assignedMember, deadline);
        } else {
            return new TodoTask(id, projectId, title, description, assignedMember, deadline);
        }
    }
}
