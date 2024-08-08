package app.SourceCode.Fundamental;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Time extends VBox {

    private static final int MAX_HOURS = 3;
    private Timeline timeline;
    private long startTime;
    private Label timeLabel;
    private Button startButton;
    private Button stopButton;

    public Time() {
        timeLabel = new Label("00:00:00");
        startButton = new Button("Start");
        stopButton = new Button("Stop");

        startButton.setOnAction(e -> startStopwatch());
        stopButton.setOnAction(e -> stopStopwatch());

        getChildren().addAll(timeLabel, startButton, stopButton);
        setSpacing(10);
        setStyle("-fx-padding: 10; -fx-alignment: center;");
        setStyle("-fx-font-size: 15");
        startStopwatch();
    }

    private void startStopwatch() {
        startTime = System.currentTimeMillis();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stopStopwatch() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private void updateTimer() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long seconds = (elapsedTime / 1000) % 60;
        long minutes = (elapsedTime / (1000 * 60)) % 60;
        long hours = (elapsedTime / (1000 * 60 * 60)) % 24;

        timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

        if (hours >= MAX_HOURS) {
            stopStopwatch();
            showAlert("Time's up!", "The timer has reached 3 hours.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
