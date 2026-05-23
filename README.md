# Flow

Flow is a gamified task management desktop application built using JavaFX.

This application helps teams manage project tasks using a Kanban workflow:

* To-Do
* On Progress
* Done

Users can gain XP, level up, unlock achievements, and compete through leaderboard rankings.

Flow was created as a Final Project for the Object Oriented Programming course.

---

# Features

## Authentication

* Login
* Register
* Role selection:

  * Manager
  * Member

---

# Role System

## Manager

Manager can:

* Create task
* Edit task
* Delete task
* Assign task to member
* Set task deadline
* Move task between statuses
* View leaderboard

## Member

Member can:

* Edit assigned task
* Delete task
* Move task between statuses
* View leaderboard

Member cannot:

* Create task
* Assign task
* Set deadline

---

# Dashboard

Dashboard includes:

* Total tasks
* Completed tasks
* Current level
* Total XP
* XP milestone progress
* Achievement preview
* Leaderboard

---

# Task Board

Kanban Board system:

* To-Do
* On Progress
* Done

Task features:

* Add task
* Edit task
* Delete task
* Assign member
* Set deadline
* Move task between statuses

Tasks can move backward or forward to prevent accidental status changes.

---

# XP System

XP is calculated based on the current task status.

Rules:

* Task moved to DONE → +20 XP
* Task moved out of DONE → -20 XP

This system prevents XP exploit and keeps progression balanced.

---

# Level System

Formula:

```bash
requiredXP = level * 100
```

Example:

* Level 1 → 100 XP
* Level 2 → 200 XP
* Level 3 → 300 XP

Levels are dynamic and can decrease if XP is reduced.

---

# Achievement System

Achievements are unlocked based on user level.

Achievements:

* Level 1 → Pemalas
* Level 2 → Rajin
* Level 3 → Deadline Hunter
* Level 4 → Master
* Level 5+ → Productivity King

---

# UI Layout

The application uses:

* Left Sidebar Navigation
* Modern Dark Mode
* Card-based Layout
* Progress Bar UI

Sidebar menu:

* Dashboard
* Task Board
* Profile
* Achievement Hall
* Logout

---

# Technologies

* Java 17+
* JavaFX
* SQLite
* JDBC SQLite Driver
* Gradle

---

# Requirements

Before running this project, make sure you have installed:

## 1. Java JDK 17 or Higher

Download:
https://www.oracle.com/java/technologies/downloads/

Verify installation:

```bash
java --version
```

---

## 2. IDE

Recommended IDE:

* Antigravity IDE
* VS Code

---

# Clone Repository

```bash
git clone https://github.com/fayyadhmuwaffaq/Flow.git
```

Move into project directory:

```bash
cd Flow
```

---

# Running the Application

Run the project using Gradle:

```bash
./gradlew run
```

For Windows:

```bash
gradlew.bat run
```

---

# Project Structure

```bash
src/
│
├── app/
├── model/
├── controller/
├── service/
├── database/
├── ui/
├── utils/
└── assets/
```

---

# Database

The application uses SQLite local database.

Database stores:

* users
* tasks
* roles
* xp
* achievements

---

# Screens

* Login
* Register
* Dashboard
* Task Board
* Profile
* Achievement Hall

---

# Future Improvements

Planned features:

* Move Task
* Better Animation
* Statistics Dashboard
* Responsive Layout
* Team Collaboration Expansion

---

# Team

* UI & JavaFX
* Logic & Gamification
* Database & Authentication

---

# License

This project is created for educational purposes.
