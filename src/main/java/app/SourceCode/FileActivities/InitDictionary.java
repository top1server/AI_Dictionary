package app.SourceCode.FileActivities;

import app.SourceCode.Fundamental.Dictionary;
import app.SourceCode.Fundamental.DictionaryManagement;
import app.SourceCode.Fundamental.Word;

import java.util.List;

public class InitDictionary extends DictionaryManagement {
    DictionaryManagement dictionaryInit = new DictionaryManagement();

    public InitDictionary() {
        dictionaryInit.insertFromFile_txt("src/main/resources/txt/dictionary.txt");
        //dictionaryInit.insertFromFile_sql();
    }

    public InitDictionary(String filePath) {
        dictionaryInit.insertFromFile_txt(filePath);
    }

    public List<Word> search(String prefix) {
        return Dictionary.getTrie().search(prefix);
    }
}
