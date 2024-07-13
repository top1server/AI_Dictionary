package app.Run;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Run extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScrollBar s = new ScrollBar();

        // Set the ScrollBar orientation and range
        s.setOrientation(javafx.geometry.Orientation.VERTICAL);
        s.setMin(0);
        s.setMax(100);

        StackPane root = new StackPane();
        root.getChildren().add(s);
        Scene scene = new Scene(root, 1280, 720);

        s.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Check if the scrollbar value is more than half of its max value
                if (newValue.doubleValue() > s.getMax() / 2) {
                    // Change to dark theme
                    scene.getRoot().setStyle("-fx-background-color: #2e2e2e; -fx-text-fill: white;");
                } else {
                    // Change to light theme
                    scene.getRoot().setStyle("");
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("ScrollBar Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
