package app.SourceCode.GUI.Controller;

import app.SourceCode.DataStructure.Trie;
import app.SourceCode.FileActivities.InitDictionary;
import app.SourceCode.Fundamental.Time;
import app.SourceCode.Fundamental.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerEditMode {
    @FXML
    private TextField oldWord, editWord;

    @FXML
    private TextFlow newDefinition;

    @FXML
    private Button deleteButton, editButton, searchMode, editMode, addMode, translateMode, gameMode;

    private TextArea definitionEditor = new TextArea();

    @FXML
    private ListView<String> listWord = new ListView<>();

    @FXML
    public void initialize() {
        newDefinition.getChildren().add(new Text("Describe new word"));
        // Thêm sự kiện cho các nút chuyển đổi chế độ
        translateMode.setOnAction(event -> switchMode("/GUI/TranslateMode.fxml"));
        searchMode.setOnAction(event -> switchMode("/GUI/SearchMode.fxml"));
        addMode.setOnAction(event -> switchMode("/GUI/AddMode.fxml"));
        gameMode.setOnAction(event -> switchMode("/GUI/GameMode.fxml"));

        deleteButton.setOnAction(event -> HandleDeleteButtonAction());
        editButton.setOnAction(event -> HandleEditButtonAction());
    }

    private void HandleDeleteButtonAction() {
        String selectedWord = listWord.getSelectionModel().getSelectedItem();
        Word word = InitDictionary.search(selectedWord).getFirst();
        if (word != null) {
            listWord.getItems().remove(selectedWord);
            InitDictionary.getTrie().delete(word);
        }
    }

    private void HandleEditButtonAction() {
        // Lấy từ đang chọn từ ListView
        String selectedWord = listWord.getSelectionModel().getSelectedItem();
        Word word = InitDictionary.search(selectedWord).getFirst();

        if (word != null) {
            // Tạo một TextArea để chỉnh sửa định nghĩa
            definitionEditor.setText(word.getWord_explain());
            definitionEditor.setWrapText(true);
            definitionEditor.setPrefSize(400, 400);

            // Clear previous content and add the TextArea to the TextFlow
            newDefinition.getChildren().clear();
            newDefinition.getChildren().add(definitionEditor);

            // Thêm nút lưu để lưu lại thay đổi
            Button saveButton = new Button("Save");
            newDefinition.getChildren().add(saveButton);

            saveButton.setOnAction(event -> {
                String updatedDefinition = definitionEditor.getText();
                Word newWord = new Word(word.getWord_target(), updatedDefinition);

                // Cập nhật định nghĩa mới trong trie
                InitDictionary.getTrie().edit(word, newWord);

                // Cập nhật giao diện để hiển thị định nghĩa mới
                newDefinition.getChildren().clear();
                newDefinition.getChildren().add(new Text(updatedDefinition));
            });
        }
    }

    @FXML
    public void SearchOldWord() throws IOException {
        String typed = oldWord.getText().toLowerCase();
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
            listWord.setFixedCellSize(80.2);
        } else {
            listWord.getItems().clear();
        }
    }

    @FXML
    public void HandleSelectItem(MouseEvent event) {
        String typed = listWord.getSelectionModel().getSelectedItem();
        List<Word> words = InitDictionary.search(typed);
        if (!words.isEmpty()) {
            Word word = words.getFirst();
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
            newDefinition.getChildren().clear();
            newDefinition.getChildren().add(new Text(otherLines.toString()));
        } else {
            newDefinition.getChildren().clear();
            newDefinition.getChildren().add(new Text("Word not found."));
        }
    }

    @FXML
    public void HandleSelectKey(KeyEvent event) {
        String typed = listWord.getSelectionModel().getSelectedItem();
        List<Word> words = InitDictionary.search(typed);
        if (!words.isEmpty()) {
            Word word = words.getFirst(); // Lấy phần tử đầu tiên nếu danh sách không rỗng
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

            newDefinition.getChildren().clear();
            newDefinition.getChildren().add(new Text(otherLines.toString()));
        } else {
            newDefinition.getChildren().clear();
            newDefinition.getChildren().add(new Text("Word not found."));
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
