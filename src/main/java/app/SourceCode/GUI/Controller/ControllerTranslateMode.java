package app.SourceCode.GUI.Controller;

import app.SourceCode.FileActivities.InitDictionary;
import app.SourceCode.Fundamental.GoogleTransAPI;
import app.SourceCode.Fundamental.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.String;
public class ControllerTranslateMode {
    @FXML
    private TextField inputTyped;

    @FXML
    private Button translateButton;

    @FXML
    private MenuButton defaultLanguage;

    @FXML
    private MenuButton customLanguage;

    @FXML
    private Button searchMode;

    @FXML
    private Button addMode;

    @FXML
    private Button editMode;

    @FXML
    private Button translateMode;

    @FXML
    private Button gameMode;

    @FXML
    private Button pDefaultLanguage;

    @FXML
    private Button pCustomLanguage;

    @FXML
    private TextFlow sourceLanguage;

    @FXML
    private TextFlow translateLanguage;

    @FXML
    private void HandleButtonClick(ActionEvent event) {
        if (event.getSource() instanceof Button clickedButton) {
            System.out.println("Button ID: " + clickedButton.getId());
        }
        if (event.getSource() instanceof MenuButton clickedMenuButton) {
            System.out.println("MenuButton ID: " + clickedMenuButton.getId());
        }
    }

    @FXML
    public void Translate() throws IOException {
        String typed = inputTyped.getText();

        if (!typed.isEmpty()) {
            if ("English".equals(defaultLanguage.getText())) {
                try {
                    String translation = GoogleTransAPI.translate(typed, "en", "vi");
                    sourceLanguage.getChildren().clear();
                    sourceLanguage.getChildren().add(new Text(typed));
                    translateLanguage.getChildren().clear();
                    translateLanguage.getChildren().add(new Text(translation));
                } catch (IOException | InterruptedException e) {
                    System.out.println("Translation failed. Error: " + e.getMessage());
                }
            } else {
                try {
                    String translation = GoogleTransAPI.translate(typed, "vi", "en");
                    sourceLanguage.getChildren().clear();
                    sourceLanguage.getChildren().add(new Text(typed));
                    translateLanguage.getChildren().clear();
                    translateLanguage.getChildren().add(new Text(translation));
                } catch (IOException | InterruptedException e) {
                    System.out.println("Translation failed. Error: " + e.getMessage());
                }
            }
        } else {
            sourceLanguage.getChildren().clear();
            translateLanguage.getChildren().clear();
        }
    }

    @FXML
    public void handleMenuItemSelection(ActionEvent event) {
        if (defaultLanguage.getText().equals("English")) {
            defaultLanguage.setText("Vietnamese");
            customLanguage.setText("English");
        } else {
            defaultLanguage.setText("English");
            customLanguage.setText("Vietnamese");
        }


    }
}
