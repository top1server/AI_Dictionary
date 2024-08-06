package app.SourceCode.GUI.Controller;

import app.SourceCode.Fundamental.GoogleTransAPI;
import app.SourceCode.Fundamental.TextToSpeech;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerTranslateMode {
    @FXML
    private TextField inputTyped;
    @FXML
    private Button translateButton, searchMode, addMode, editMode, translateMode, gameMode, pDefaultLanguage;
    @FXML
    private MenuButton defaultLanguage, customLanguage;
    @FXML
    private TextFlow sourceLanguage, translateLanguage;

    @FXML
    private void initialize() {
        // Gán sự kiện cho nút translateButton
        translateButton.setOnAction(event -> {
            try {
                Translate(); // Gọi phương thức dịch thuật
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Gán sự kiện cho các nút chuyển đổi chế độ
        searchMode.setOnAction(event -> switchMode("/GUI/searchMode.fxml"));
        addMode.setOnAction(event -> switchMode("/GUI/addMode.fxml"));
        editMode.setOnAction(event -> switchMode("/GUI/editMode.fxml"));
        gameMode.setOnAction(event -> switchMode("/GUI/gameMode.fxml"));
        pDefaultLanguage.setOnAction(event -> HandleVoiceButtonClick());
    }

    @FXML
    private void HandleButtonClick(ActionEvent event) {
        if (event.getSource() instanceof Button clickedButton) {
            System.out.println("Button ID: " + clickedButton.getId());
        }
    }

    @FXML
    private void Translate() throws IOException {
        String typed = inputTyped.getText();
        if (typed.isEmpty()) {
            sourceLanguage.getChildren().clear();
            translateLanguage.getChildren().clear();
            return;
        }

        String fromLang = "English".equals(defaultLanguage.getText()) ? "en" : "vi";
        String toLang = "English".equals(defaultLanguage.getText()) ? "vi" : "en";

        try {
            String translation = GoogleTransAPI.translate(typed, fromLang, toLang);
            sourceLanguage.getChildren().setAll(new Text(typed));
            translateLanguage.getChildren().setAll(new Text(translation));
        } catch (IOException | InterruptedException e) {
            System.out.println("Translation failed. Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleMenuItemSelection(ActionEvent event) {
        if ("English".equals(defaultLanguage.getText())) {
            defaultLanguage.setText("Vietnamese");
            customLanguage.setText("English");
        } else {
            defaultLanguage.setText("English");
            customLanguage.setText("Vietnamese");
        }
    }

    private void switchMode(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) translateMode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void HandleVoiceButtonClick() {
        String typed = inputTyped.getText();
        if (typed != null && !typed.isEmpty()) {
            TextToSpeech.voice(typed);
        }
    }
}
