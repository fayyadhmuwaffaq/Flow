package flow.app;

import flow.ui.LoginView;
import flow.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneManager.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Flow - Task Management");
        
        LoginView loginView = new LoginView();
        SceneManager.switchScene(loginView);
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
