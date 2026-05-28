package flow.utils;

import flow.model.Project;
import flow.ui.MainLayout;
import flow.ui.TaskBoardView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class SceneManager {
    private static Stage primaryStage;
    private static MainLayout mainLayout;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void setMainLayout(MainLayout layout) {
        mainLayout = layout;
    }

    public static void switchScene(Parent root) {
        if (primaryStage != null) {
            if (root instanceof MainLayout) {
                mainLayout = (MainLayout) root;
            }
            Scene scene = primaryStage.getScene();
            if (scene == null) {
                scene = new Scene(root, 1200, 800);
                primaryStage.setScene(scene);
            } else {
                scene.setRoot(root);
            }
            primaryStage.centerOnScreen();
            primaryStage.show();
        }
    }

    public static void switchToProjectTaskBoard(Project project) {
        if (mainLayout != null) {
            mainLayout.highlightButton("Projects");
            mainLayout.setContent("Task Board: " + project.getName(), new TaskBoardView(project).getView());
        }
    }
}
