package app.ai_dictionary;

import app.SourceCode.Fundamental.Time;
import app.SourceCode.FileActivities.InitDictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            InitDictionary initDictionary = new InitDictionary();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/SearchMode.fxml"));
            Parent root = loader.load();

            AnchorPane anchorPane = (AnchorPane) root;
            Time time = new Time();
            AnchorPane.setBottomAnchor(time, 580.0);
            AnchorPane.setRightAnchor(time, 10.0);
            anchorPane.getChildren().add(time);

            Scene scene = new Scene(root);
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/IconApp.png"))));
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
