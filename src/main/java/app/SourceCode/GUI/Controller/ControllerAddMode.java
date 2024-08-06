package app.SourceCode.GUI.Controller;

import app.SourceCode.FileActivities.InitDictionary;
import app.SourceCode.FileActivities.ReadFromFile;
import app.SourceCode.FileActivities.WriteToFile;
import app.SourceCode.Fundamental.Word;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerAddMode {
    @FXML
    private TextArea definition;
    @FXML
    private Button searchMode, addMode, editMode, translateMode, gameMode, addButton;
    @FXML
    private TextField newWord, message;
    @FXML
    private ListView<String> vocabulary = new ListView<>();

    InitDictionary initDictionary = new InitDictionary("src/main/resources/txt/dictionary_add.txt");
    @FXML
    public void initialize() {
        updateVocabularyList();
        message.setText("Message");
        vocabulary.setFixedCellSize(68.7);

        addButton.setOnAction(event -> {
            try {
                addWord(); // Gọi phương thức addWord khi addButton được nhấn
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Thêm sự kiện cho phím Enter trong TextField
        newWord.setOnKeyPressed(this::Add);
        definition.setOnKeyPressed(this::Add);

        // Thêm sự kiện cho các nút chuyển đổi chế độ
        translateMode.setOnAction(event -> switchMode("/GUI/TranslateMode.fxml"));
        searchMode.setOnAction(event -> switchMode("/GUI/SearchMode.fxml"));
        editMode.setOnAction(event -> switchMode("/GUI/EditMode.fxml"));
        gameMode.setOnAction(event -> switchMode("/GUI/GameMode.fxml"));
    }

    @FXML
    public void Add(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                addWord();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addWord() throws IOException {
        String typed = newWord.getText().toLowerCase();
        String defined = definition.getText();
        if (!typed.isEmpty()) {
            WriteToFile.writeTXT("src/main/resources/txt/dictionary_add.txt", typed + "\t" + defined);
            updateVocabularyList();
            initDictionary.addWord(new Word(typed, defined));
            message.setText("Successful!");
            delayAction(1.0, () -> message.setText("Message"));
        }
    }

    private void updateVocabularyList() {
        List<Word> newWordList = ReadFromFile.readTXT("src/main/resources/txt/dictionary_add.txt");
        ObservableList<String> words = FXCollections.observableArrayList(
                newWordList.stream()
                        .map(Word::getWord_target)
                        .distinct()
                        .collect(Collectors.toList())
        );
        vocabulary.setItems(words);
    }

    private void delayAction(double seconds, Runnable action) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(event -> action.run());
        pause.play();
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
    public void HandleSelectItem(MouseEvent event) {
        String typed = vocabulary.getSelectionModel().getSelectedItem();
        Word word = InitDictionary.search(typed).getFirst();
        String tmp1 = word.getWord_explain();
        definition.clear();
        definition.setText(tmp1);
    }

    @FXML
    public void HandleSelectKey(KeyEvent event) {
        String typed = vocabulary.getSelectionModel().getSelectedItem();
        Word word = InitDictionary.search(typed).getFirst();
        String tmp1 = word.getWord_explain();
        definition.clear();
        definition.setText(tmp1);
    }
}
