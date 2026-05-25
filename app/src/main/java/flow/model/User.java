package flow.model;

public abstract class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private int level;
    private int xp;

    public User(int id, String username, String password, String role, int level, int xp) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.level = level;
        this.xp = xp;
    }

    // ID
    public int getId() {
        return id;
    }
    public void setId(int id) { 
        this.id = id; 
    }

    // username
    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }

    // Password
    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    // Role
    public String getRole() { 
        return role; 
    }
    public void setRole(String role) { 
        this.role = role; 
    }

    // Level
    public int getLevel() { 
        return level; 
    }
    public void setLevel(int level) { 
        this.level = level; 
    }

    // Level
    public int getXp() { 
        return xp; 
    }
    public void setXp(int xp) { 
        this.xp = xp; 
    }
}
