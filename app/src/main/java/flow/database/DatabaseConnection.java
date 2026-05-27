package flow.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static Connection connection = null;
    private static final String URL = "jdbc:sqlite:flow.db";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
                initializeDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static void initializeDatabase() {
        try (Statement statement = connection.createStatement()) {
            // Create users table
            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL," +
                    "role TEXT NOT NULL," +
                    "level INTEGER DEFAULT 1," +
                    "xp INTEGER DEFAULT 0)");

            // Create tasks table
            statement.execute("CREATE TABLE IF NOT EXISTS tasks (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "project_id INTEGER NOT NULL," +
                    "title TEXT NOT NULL," +
                    "description TEXT," +
                    "status TEXT NOT NULL," +
                    "assigned_member TEXT," +
                    "deadline TEXT," +
                    "FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE)");

            // Create projects table
            statement.execute("CREATE TABLE IF NOT EXISTS projects (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "description TEXT," +
                    "manager_id INTEGER NOT NULL," +
                    "FOREIGN KEY (manager_id) REFERENCES users(id))");

            // Create project_members table
            statement.execute("CREATE TABLE IF NOT EXISTS project_members (" +
                    "project_id INTEGER NOT NULL," +
                    "user_id INTEGER NOT NULL," +
                    "PRIMARY KEY (project_id, user_id)," +
                    "FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
