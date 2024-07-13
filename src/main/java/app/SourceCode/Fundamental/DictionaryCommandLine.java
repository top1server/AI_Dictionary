package app.SourceCode.Fundamental;

import java.util.ArrayList;
import java.util.List;

public class DictionaryCommandLine extends Dictionary {

    protected DictionaryManagement dictionaryManagement = new DictionaryManagement();

    public void showAllWord() {
        List<Word> tmp = Dictionary.getTrie().printAllWords();
        int ID = 1;
        System.out.printf("%-10s| %-30s| %-100s\n", "No", "English", "Vietnamese");
        for (Word word: tmp) {
            System.out.printf("%-10d| %-30s| %-100s\n", ID, word.getWord_target(), word.getWord_explain());
            ID++;
        }
    }

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        showAllWord();
    }

    public List<Word> dictionarySearcher(String prefix) {
         return dictionaryManagement.dictionaryLookup(prefix);
    }






}
