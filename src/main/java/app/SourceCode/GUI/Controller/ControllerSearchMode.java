package app.SourceCode.GUI.Controller;

import app.SourceCode.FileActivities.InitDictionary;
import app.SourceCode.Fundamental.TextToSpeech;
import app.SourceCode.Fundamental.Time;
import app.SourceCode.Fundamental.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerSearchMode {
    @FXML
    private Button searchButton, searchMode, addMode, editMode, translateMode, gameMode, voiceButton;

    @FXML
    private MenuButton defaultLanguage, customLanguage;

    @FXML
    private TextField inputTyped;

    @FXML
    private TextFlow definition, relation;

    @FXML
    private ListView<String> listWord = new ListView<>();

    @FXML
    public void initialize() {
        // Set cell factory for ListView
        listWord.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item);
                        }
                    }
                };
            }
        });
        translateMode.setOnAction(event -> switchMode("/GUI/TranslateMode.fxml"));
        addMode.setOnAction(event -> switchMode("/GUI/AddMode.fxml"));
        editMode.setOnAction(event -> switchMode("/GUI/EditMode.fxml"));
        gameMode.setOnAction(event -> switchMode("/GUI/GameMode.fxml"));
        voiceButton.setOnAction(event -> HandleVoiceButtonClick());
    }

    @FXML
    public void Search(KeyEvent e) throws IOException {
        String typed = inputTyped.getText().toLowerCase();
        List<Word> wordSearch = InitDictionary.search(typed);
        ObservableList<Word> tmp1 = FXCollections.observableArrayList();
        tmp1.addAll(wordSearch);

        if (!typed.isEmpty()) {
            ObservableList<Word> tmp2 = FXCollections.observableArrayList(tmp1.stream().distinct().collect(Collectors.toList()));
            ObservableList<String> tmp3 = FXCollections.observableArrayList();
            for (Word word: tmp2) {
                tmp3.add(word.getWord_target());
            }
            listWord.getItems().clear();
            listWord.setItems(tmp3);
            listWord.setFixedCellSize(77.4);
        } else {
            listWord.getItems().clear();
            definition.getChildren().clear();
            relation.getChildren().clear();
        }
        if (e.getCode() == KeyCode.BACK_SPACE) {
            definition.getChildren().clear();
            relation.getChildren().clear();
        }
    }

    @FXML
    public void HandleSelectItem(MouseEvent event) {
        String typed = listWord.getSelectionModel().getSelectedItem();
        Word word = InitDictionary.search(typed).getFirst();
        String tmp1 = word.getWord_explain();

        String[] lines = tmp1.split("\n");
        StringBuilder filteredLines = new StringBuilder();
        StringBuilder otherLines = new StringBuilder();

        for (String line : lines) {
            if (line.startsWith("~")) {
                filteredLines.append(line).append("\n");
            } else {
                otherLines.append(line).append("\n");
            }
        }

        if (!typed.isEmpty()) {
            definition.getChildren().clear();
            definition.getChildren().add(new Text(otherLines.toString()));
            relation.getChildren().clear();
            relation.getChildren().add(new Text(filteredLines.toString()));
        } else {
            definition.getChildren().clear();
            relation.getChildren().clear();
        }
    }

    @FXML
    public void HandleSelectKey(KeyEvent event) {
        String typed = listWord.getSelectionModel().getSelectedItem();
        Word word = InitDictionary.search(typed).getFirst();
        String tmp1 = word.getWord_explain();

        String[] lines = tmp1.split("\n");
        StringBuilder filteredLines = new StringBuilder();
        StringBuilder otherLines = new StringBuilder();

        for (String line : lines) {
            if (line.startsWith("~")) {
                filteredLines.append(line).append("\n");
            } else {
                otherLines.append(line).append("\n");
            }
        }
        if (!typed.isEmpty()) {
            definition.getChildren().clear();
            definition.getChildren().add(new Text(otherLines.toString()));
            relation.getChildren().clear();
            relation.getChildren().add(new Text(filteredLines.toString()));
        } else {
            definition.getChildren().clear();
            relation.getChildren().clear();
        }
    }

    @FXML
    private void HandleVoiceButtonClick() {
        String typed = listWord.getSelectionModel().getSelectedItem();
        if (typed != null && !typed.isEmpty()) {
            TextToSpeech.voice(typed);
        }
    }

    private void switchMode(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            AnchorPane anchorPane = (AnchorPane) root;
            Time time = new Time();
            AnchorPane.setBottomAnchor(time, 580.0);
            AnchorPane.setRightAnchor(time, 10.0);
            anchorPane.getChildren().add(time);
            Stage stage = (Stage) translateMode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
