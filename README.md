# TaskRaid

TaskRaid is a gamified task management desktop application built using JavaFX.

This application helps users manage project tasks using a simple Kanban workflow:

* To-Do
* On Progress
* Done

Users gain XP by completing tasks, level up through milestone progression, and unlock achievements based on their productivity.

TaskRaid was created as a Final Project for the Object Oriented Programming course.

---

## Features

### Authentication

* Login
* Register
* User validation

### Dashboard

* Total tasks
* Completed tasks
* Current level
* XP milestone progress

### Task Board

* Add task
* Edit task
* Delete task
* Move task between statuses

### Gamification System

* +20 XP for completed tasks
* Dynamic level progression
* XP reduction when tasks move out of DONE
* Achievement unlocking system

### Achievement Hall

Unlock achievements based on user level:

* Pemalas
* Rajin
* Deadline Hunter
* Master
* Productivity King

---

## Technologies

* Java 17+
* JavaFX
* SQLite
* JDBC SQLite Driver

---

## Requirements

Before running this project, make sure you have installed:

### 1. Java JDK 17 or higher

Download:
https://www.oracle.com/java/technologies/downloads/

Verify installation:

```bash
java --version
```

---

### 3. IDE

Recommended:

* IntelliJ IDEA
* VS Code

---

## Clone Repository

```bash
git clone https://github.com/fayyadhmuwaffaq/Flow.git
```

Move into project directory:

```bash
cd Flow
```

---

## Running the Application

Run:

```bash
./gradlew run
```

---

## Project Structure

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

## XP System

XP is calculated based on the current task status.

Rules:

* Task moved to DONE → +20 XP
* Task moved out of DONE → -20 XP

This prevents XP exploit and keeps progression balanced.

---

## Level Progression

Level requirements use the formula:

```bash
requiredXP = level * 100
```

Example:

* Level 1 → 100 XP
* Level 2 → 200 XP
* Level 3 → 300 XP

Levels are dynamic and can decrease if XP is reduced.

---

## Future Improvements

Planned features:

* Move Task
* Better Animation
* Statistics Dashboard
* Team Collaboration
* Responsive Layout

---

## Team

* UI & JavaFX
* Logic & Gamification
* Database & Authentication

---

## License

This project is created for educational purposes.
