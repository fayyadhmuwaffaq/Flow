package flow.ui;

import flow.controller.ProjectController;
import flow.model.Project;
import flow.model.User;
import flow.utils.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class ProjectListView extends VBox {
    private ProjectController controller;
    private FlowPane projectGrid;

    public ProjectListView() {
        this.controller = new ProjectController();
        setSpacing(20);
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #1E1E2F;");

        setupHeader();
        setupProjectGrid();
        refreshProjects();
    }

    private void setupHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);

        Label titleLabel = new Label("Projects");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(titleLabel, spacer);

        if ("Manager".equalsIgnoreCase(controller.getCurrentUser().getRole())) {
            Button createBtn = new Button("Create Project");
            createBtn.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8;");
            createBtn.setOnAction(e -> showCreateProjectDialog());
            header.getChildren().add(createBtn);
        }

        getChildren().add(header);
    }

    private void setupProjectGrid() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        projectGrid = new FlowPane();
        projectGrid.setHgap(20);
        projectGrid.setVgap(20);
        projectGrid.setPadding(new Insets(10));
        projectGrid.setAlignment(Pos.TOP_LEFT);

        scrollPane.setContent(projectGrid);
        getChildren().add(scrollPane);
    }

    private void refreshProjects() {
        projectGrid.getChildren().clear();
        List<Project> projects = controller.getProjects();
        for (Project project : projects) {
            projectGrid.getChildren().add(createProjectCard(project));
        }
    }

    private VBox createProjectCard(Project project) {
        VBox card = new VBox(15);
        card.setPrefSize(280, 180);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #2A2A40; -fx-background-radius: 15; -fx-cursor: hand;");

        Label nameLabel = new Label(project.getName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        nameLabel.setWrapText(true);

        Label descLabel = new Label(project.getDescription());
        descLabel.setStyle("-fx-text-fill: #A0A0B8; -fx-font-size: 14px;");
        descLabel.setWrapText(true);
        VBox.setVgrow(descLabel, Priority.ALWAYS);

        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER_RIGHT);

        if ("Manager".equalsIgnoreCase(controller.getCurrentUser().getRole())) {
            Button manageMembersBtn = new Button("Manage Members");
            manageMembersBtn.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");
            
            manageMembersBtn.setOnMouseEntered(e -> 
                manageMembersBtn.setStyle("-fx-background-color: #927BFF; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;")
            );
            manageMembersBtn.setOnMouseExited(e -> 
                manageMembersBtn.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;")
            );

            manageMembersBtn.setOnAction(e -> {
                e.consume();
                showManageMembersDialog(project);
            });
            footer.getChildren().add(manageMembersBtn);
        }

        card.getChildren().addAll(nameLabel, descLabel, footer);

        card.setOnMouseClicked(e -> {
            SceneManager.switchToProjectTaskBoard(project);
        });

        // Hover effect
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #353550; -fx-background-radius: 15; -fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: #2A2A40; -fx-background-radius: 15; -fx-cursor: hand;"));

        return card;
    }

    private void showCreateProjectDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Create New Project");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1E1E2F;");

        TextField nameField = new TextField();
        nameField.setPromptText("Project Name");
        nameField.setStyle("-fx-background-color: #2A2A40; -fx-text-fill: white; -fx-prompt-text-fill: #606070;");

        TextArea descArea = new TextArea();
        descArea.setPromptText("Description");
        descArea.setPrefRowCount(3);
        descArea.setStyle("-fx-control-inner-background: #2A2A40; -fx-text-fill: white; -fx-prompt-text-fill: #606070;");

        Button saveBtn = new Button("Create");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10;");
        saveBtn.setOnAction(e -> {
            if (!nameField.getText().isEmpty()) {
                controller.createProject(nameField.getText(), descArea.getText());
                dialog.close();
                refreshProjects();
            }
        });

        layout.getChildren().addAll(new Label("Project Name") {{ setStyle("-fx-text-fill: white;"); }}, nameField,
                                    new Label("Description") {{ setStyle("-fx-text-fill: white;"); }}, descArea, saveBtn);

        Scene scene = new Scene(layout, 400, 350);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void showManageMembersDialog(Project project) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Manage Members - " + project.getName());

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1E1E2F;");

        Label label = new Label("Add/Remove Members");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<User> memberListView = new ListView<>();
        memberListView.setStyle("-fx-background-color: #2A2A40; -fx-control-inner-background: #2A2A40;");
        
        List<User> allMembers = controller.getAllMembers();
        List<User> currentMembers = controller.getProjectMembers(project.getId());

        memberListView.setCellFactory(lv -> new ListCell<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    Label nameLabel = new Label(user.getUsername());
                    nameLabel.setStyle("-fx-text-fill: white;");
                    
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);
                    
                    CheckBox checkBox = new CheckBox();
                    boolean isMember = currentMembers.stream().anyMatch(m -> m.getId() == user.getId());
                    checkBox.setSelected(isMember);
                    
                    checkBox.setOnAction(e -> {
                        if (checkBox.isSelected()) {
                            controller.addMemberToProject(project.getId(), user.getId());
                        } else {
                            controller.removeMemberFromProject(project.getId(), user.getId());
                        }
                    });
                    
                    hBox.getChildren().addAll(nameLabel, spacer, checkBox);
                    setGraphic(hBox);
                }
            }
        });

        memberListView.getItems().addAll(allMembers);

        Button closeBtn = new Button("Close");
        closeBtn.setMaxWidth(Double.MAX_VALUE);
        closeBtn.setStyle("-fx-background-color: #7B61FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10;");
        closeBtn.setOnAction(e -> dialog.close());

        layout.getChildren().addAll(label, memberListView, closeBtn);

        Scene scene = new Scene(layout, 400, 500);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
