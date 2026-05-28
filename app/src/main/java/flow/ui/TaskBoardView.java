package flow.ui;

import flow.controller.TaskBoardController;
import flow.model.Project;
import flow.model.Task;
import flow.utils.AlertHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;
import java.util.List;

public class TaskBoardView {

    private TaskBoardController controller;
    private Project project;
    private VBox root;
    private HBox board;
    private VBox todoColumn;
    private VBox progressColumn;
    private VBox doneColumn;
    private VBox todoList;
    private VBox progressList;
    private VBox doneList;

    public TaskBoardView(Project project) {
        this.project = project;
        this.controller = new TaskBoardController(project.getId());
        initView();
    }

    private void initView() {
        root = new VBox(20);
        root.setPadding(new Insets(10, 0, 10, 0));
        root.setStyle("-fx-background-color: #1E1E2F;");

        Label projectHeader = new Label("Project: " + project.getName());
        projectHeader.setFont(Font.font("System", FontWeight.BOLD, 22));
        projectHeader.setTextFill(Color.WHITE);
        root.getChildren().add(projectHeader);

        if (controller.isManager()) {
            Button addTaskBtn = new Button("+ Add New Task");
            addTaskBtn.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
            addTaskBtn.setOnAction(e -> showAddTaskDialog());
            root.getChildren().add(addTaskBtn);
        }

        board = new HBox(20);
        board.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(board, Priority.ALWAYS);

        todoList = new VBox(10);
        progressList = new VBox(10);
        doneList = new VBox(10);

        todoColumn = createColumn("To-Do", "#FF5252", todoList);
        progressColumn = createColumn("On Progress", "#FFD600", progressList);
        doneColumn = createColumn("Done", "#00C853", doneList);

        board.getChildren().addAll(todoColumn, progressColumn, doneColumn);
        root.getChildren().add(board);

        refreshBoard();
    }

    private VBox createColumn(String title, String color, VBox taskList) {
        VBox col = new VBox(15);
        col.setPadding(new Insets(15));
        col.setStyle("-fx-background-color: #2A2A40; -fx-background-radius: 12;");
        col.setPrefWidth(350);
        col.setMinWidth(300);
        HBox.setHgrow(col, Priority.ALWAYS);

        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.web(color));
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + color + ";");
        
        taskList.setPadding(new Insets(0, 5, 0, 0));

        ScrollPane scroll = new ScrollPane(taskList);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-viewport-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scroll, Priority.ALWAYS);
        
        col.getChildren().addAll(titleLabel, sep, scroll);
        return col;
    }

    private void refreshBoard() {
        todoList.getChildren().clear();
        progressList.getChildren().clear();
        doneList.getChildren().clear();

        List<Task> tasks = controller.getAllTasks();
        for (Task task : tasks) {
            VBox card = createTaskCard(task);
            if ("To-Do".equalsIgnoreCase(task.getStatus())) {
                todoList.getChildren().add(card);
            } else if ("On Progress".equalsIgnoreCase(task.getStatus())) {
                progressList.getChildren().add(card);
            } else if ("Done".equalsIgnoreCase(task.getStatus())) {
                doneList.getChildren().add(card);
            }
        }
    }

    private VBox createTaskCard(Task task) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #3A3A55; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        Label title = new Label(task.getTitle());
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("System", FontWeight.BOLD, 16));
        title.setWrapText(true);

        Label desc = new Label(task.getDescription());
        desc.setTextFill(Color.web("#A0A0B8"));
        desc.setFont(Font.font(13));
        desc.setWrapText(true);

        Label meta = new Label("Assign: " + (task.getAssignedMember() != null ? task.getAssignedMember() : "Unassigned"));
        meta.setTextFill(Color.web("#7B61FF"));
        meta.setFont(Font.font(12));

        Label deadline = new Label("Deadline: " + (task.getDeadline() != null ? task.getDeadline().toString() : "No Deadline"));
        deadline.setTextFill(Color.web("#FF5252"));
        deadline.setFont(Font.font(12));

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);
        
        Button editBtn = createIconButton("Edit", "#FFD600");
        Button deleteBtn = createIconButton("Del", "#FF5252");
        
        editBtn.setOnAction(e -> showEditTaskDialog(task));
        deleteBtn.setOnAction(e -> {
            controller.deleteTask(task.getId());
            refreshBoard();
        });

        HBox moveActions = new HBox(5);
        if (!"To-Do".equalsIgnoreCase(task.getStatus())) {
            Button toTodo = createIconButton("←", "#A0A0B8");
            toTodo.setOnAction(e -> {
                controller.moveTask(task, "To-Do");
                refreshBoard();
            });
            moveActions.getChildren().add(toTodo);
        }
        if (!"Done".equalsIgnoreCase(task.getStatus())) {
            Button toNext = createIconButton("→", "#7B61FF");
            String nextStatus = "To-Do".equalsIgnoreCase(task.getStatus()) ? "On Progress" : "Done";
            toNext.setOnAction(e -> {
                controller.moveTask(task, nextStatus);
                refreshBoard();
            });
            moveActions.getChildren().add(toNext);
        } else {
             Button toPrev = createIconButton("←", "#A0A0B8");
             toPrev.setOnAction(e -> {
                controller.moveTask(task, "On Progress");
                refreshBoard();
            });
            moveActions.getChildren().add(toPrev);
        }

        actions.getChildren().addAll(moveActions, new Pane(), editBtn, deleteBtn);
        HBox.setHgrow(actions.getChildren().get(1), Priority.ALWAYS);

        card.getChildren().addAll(title, desc, meta, deadline, actions);
        return card;
    }

    private Button createIconButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + color + "; -fx-border-color: " + color + "; -fx-border-radius: 5; -fx-font-size: 11; -fx-cursor: hand;");
        return btn;
    }

    private void showAddTaskDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add New Task");
        
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField titleField = new TextField();
        TextArea descField = new TextArea();
        descField.setPrefRowCount(3);
        TextField memberField = new TextField();
        DatePicker deadlinePicker = new DatePicker();
        
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descField, 1, 1);
        grid.add(new Label("Assign Member:"), 0, 2);
        grid.add(memberField, 1, 2);
        grid.add(new Label("Deadline:"), 0, 3);
        grid.add(deadlinePicker, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                controller.addTask(titleField.getText(), descField.getText(), memberField.getText(), deadlinePicker.getValue());
                refreshBoard();
            }
            return null;
        });
        
        dialog.showAndWait();
    }

    private void showEditTaskDialog(Task task) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Edit Task");
        
        ButtonType saveButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField titleField = new TextField(task.getTitle());
        TextArea descField = new TextArea(task.getDescription());
        descField.setPrefRowCount(3);
        TextField memberField = new TextField(task.getAssignedMember());
        DatePicker deadlinePicker = new DatePicker(task.getDeadline());
        
        if (!controller.isManager()) {
            memberField.setEditable(false);
            deadlinePicker.setDisable(true);
        }
        
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descField, 1, 1);
        grid.add(new Label("Assign Member:"), 0, 2);
        grid.add(memberField, 1, 2);
        grid.add(new Label("Deadline:"), 0, 3);
        grid.add(deadlinePicker, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                controller.updateTask(task, titleField.getText(), descField.getText(), memberField.getText(), deadlinePicker.getValue());
                refreshBoard();
            }
            return null;
        });
        
        dialog.showAndWait();
    }

    public Node getView() {
        return root;
    }
}
