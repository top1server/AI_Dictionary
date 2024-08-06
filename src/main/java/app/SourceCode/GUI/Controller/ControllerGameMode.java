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

import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerGameMode {
    @FXML
    private Button searchButton, searchMode, addMode, editMode, translateMode, gameMode;

    @FXML
    private MenuButton defaultLanguage, customLanguage;

    @FXML
    private TextField inputTyped;

    @FXML
    private TextFlow definition;

    @FXML
    private ListView<String> listWord = new ListView<>();

    @FXML
    private Text wordDisplay;
    @FXML
    private TextField guessInput;
    @FXML
    private Button submitGuessButton;
    @FXML
    private Label statusLabel;

    private String selectedWord;
    private StringBuilder displayWord;
    private List<Character> guessedLetters;
    private int guessesRemaining = 10;


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
        searchMode.setOnAction(event -> switchMode("/GUI/SearchMode.fxml"));
        loadNewWord();
        submitGuessButton.setOnAction(event -> handleGuess());
    }

    private void loadNewWord() {
        List<Word> words = InitDictionary.search("");
        if (words.isEmpty()) {
            statusLabel.setText("No words available.");
            return;
        }

        Random random = new Random();
        Word word = words.get(random.nextInt(words.size()));
        selectedWord = word.getWord_target().toLowerCase();
        displayWord = new StringBuilder("_".repeat(selectedWord.length()));
        guessedLetters = new ArrayList<>();
        updateWordDisplay();
    }

    private void handleGuess() {
        String guess = guessInput.getText().toLowerCase();
        if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
            statusLabel.setText("Invalid guess. Please enter a single letter.");
            return;
        }

        char guessedLetter = guess.charAt(0);
        if (guessedLetters.contains(guessedLetter)) {
            statusLabel.setText("You already guessed that letter.");
            return;
        }

        guessedLetters.add(guessedLetter);
        boolean correctGuess = false;

        // Update displayWord with correct guesses
        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == guessedLetter) {
                displayWord.setCharAt(i, guessedLetter);
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            guessesRemaining--;
        }

        if (displayWord.indexOf("_") == -1) {
            statusLabel.setText("Congratulations! You've guessed the word.");
        } else if (guessesRemaining <= 0) {
            statusLabel.setText("Game over! The word was: " + selectedWord);
        } else {
            statusLabel.setText("Guesses remaining: " + guessesRemaining);
        }

        updateWordDisplay();
        guessInput.clear();
    }

    private void updateWordDisplay() {
        wordDisplay.setText(displayWord.toString());
    }

    @FXML
    public void Search() throws IOException {
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
        definition.getChildren().clear();
        definition.getChildren().add(new Text(otherLines.toString()));
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
        definition.getChildren().clear();
        definition.getChildren().add(new Text(otherLines.toString()));
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
