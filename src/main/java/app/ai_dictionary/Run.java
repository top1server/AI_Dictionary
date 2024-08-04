package app.ai_dictionary;

import app.SourceCode.FileActivities.ReadFromFile;
import app.SourceCode.FileActivities.WriteToFile;
import app.SourceCode.Fundamental.Dictionary;
import app.SourceCode.Fundamental.Word;

import java.util.List;

public class Run {
    public static void main(String[] args) {
        List<Word> wordList = ReadFromFile.readSQL();
        for (Word word: wordList) {
            System.out.println(word.getWord_target() + "\t" +
                    "" + word.getWord_explain());
        }
    }
}
