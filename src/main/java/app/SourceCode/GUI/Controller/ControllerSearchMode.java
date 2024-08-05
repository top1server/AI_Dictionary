package app.SourceCode.GUI.Controller;

import app.SourceCode.FileActivities.InitDictionary;
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

public class ControllerSearchMode {
    @FXML
    private Button searchButton, searchMode, addMode, editMode, translateMode, gameMode;

    @FXML
    private MenuButton defaultLanguage, customLanguage;

    @FXML
    private TextField inputTyped;

    @FXML
    private TextFlow definition, relation;

    InitDictionary initDictionary = new InitDictionary();

    @FXML
    private ListView<String> ListWord = new ListView<>();

    @FXML
    public void initialize() {
        // Set cell factory for ListView
        ListWord.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
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
    }

    @FXML
    public void Search() throws IOException {
        String typed = inputTyped.getText().toLowerCase();
        List<Word> wordSearch = initDictionary.search(typed);
        ObservableList<Word> tmp1 = FXCollections.observableArrayList();
        tmp1.addAll(wordSearch);

        if (!typed.isEmpty()) {
            ObservableList<Word> tmp2 = FXCollections.observableArrayList(tmp1.stream().distinct().collect(Collectors.toList()));
            ObservableList<String> tmp3 = FXCollections.observableArrayList();
            for (Word word: tmp2) {
                tmp3.add(word.getWord_target());
            }
            ListWord.getItems().clear();
            ListWord.setItems(tmp3);
            ListWord.setFixedCellSize(77.4);
        } else {
            ListWord.getItems().clear();
        }
    }

    @FXML
    public void HandleSelectItem(MouseEvent event) {
        String typed = ListWord.getSelectionModel().getSelectedItem();
        Word word = initDictionary.search(typed).getFirst();
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
        definition.getChildren().clear();
        definition.getChildren().add(new Text(otherLines.toString()));
        relation.getChildren().clear();
        relation.getChildren().add(new Text(filteredLines.toString()));
    }

    @FXML
    public void HandleSelectKey(KeyEvent event) {
        String typed = ListWord.getSelectionModel().getSelectedItem();
        Word word = initDictionary.search(typed).getFirst();
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
        definition.getChildren().clear();
        definition.getChildren().add(new Text(otherLines.toString()));
        relation.getChildren().clear();
        relation.getChildren().add(new Text(filteredLines.toString()));
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



}
