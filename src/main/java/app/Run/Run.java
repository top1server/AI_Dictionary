package app.Run;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


import java.io.InputStream;

public class Run extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load icon
        InputStream iconStream = getClass().getResourceAsStream("/image/icon_app.png");
        assert iconStream != null;
        Image icon = new Image(iconStream);

        // Set icon for the primary stage
        primaryStage.getIcons().add(icon);

        // Create the root layout (in this case, a StackPane)
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        // Add your application setup code here...

        // Set the scene and show the primary stage
        primaryStage.setTitle("JavaFX Application with Icon");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

