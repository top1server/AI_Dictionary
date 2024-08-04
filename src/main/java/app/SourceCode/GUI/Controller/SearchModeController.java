package app.SourceCode.GUI.Controller;

import app.SourceCode.FileActivities.InitDictionary;
import app.SourceCode.Fundamental.Dictionary;
import app.SourceCode.Fundamental.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchModeController {

    @FXML
    private Button searchButton;

    @FXML
    private Button translateButton;

    @FXML
    private Button searchMode;

    @FXML
    private Button add_NewWordsMode;

    @FXML
    private Button editMode;

    @FXML
    private Button translateMode;

    @FXML
    private Button gameMode;

    @FXML
    private MenuButton defaultLanguage;

    @FXML
    private MenuButton customButton;

    @FXML
    private TextField inputTyped;

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
    }
    @FXML
    private TextFlow definition;

    InitDictionary initDictionary = new InitDictionary();

    @FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() instanceof Button clickedButton) {
            System.out.println("Button ID: " + clickedButton.getId());
        }
        if (event.getSource() instanceof MenuButton clickedMenuButton) {
            System.out.println("MenuButton ID: " + clickedMenuButton.getId());
        }
    }

    @FXML
    public void Typing(ActionEvent event) {
        if (event.getSource() == inputTyped) {
            System.out.println("please wait");
        }
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
        System.out.println(word.getWord_explain());
    }


}
