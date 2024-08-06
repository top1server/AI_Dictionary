package app.SourceCode.GUI.Controller;

import app.SourceCode.FileActivities.InitDictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            InitDictionary initDictionary = new InitDictionary();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/GUI/SearchMode.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("AI Dictionary");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

