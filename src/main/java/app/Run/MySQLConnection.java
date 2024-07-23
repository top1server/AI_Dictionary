package app.Run;

import app.SourceCode.FileActivities.ReadFromFile;
import app.SourceCode.Fundamental.Word;

import java.util.ArrayList;
import java.util.List;

public class MySQLConnection {
    public static void main(String[] args) {
        List<Word> ans = new ArrayList<>();
        ans = ReadFromFile.readSQL();
        for (Word word: ans) {
            System.out.println(word.getWord_explain());
        }
    }
}
