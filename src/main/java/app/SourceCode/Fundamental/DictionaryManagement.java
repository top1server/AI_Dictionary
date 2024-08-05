package app.SourceCode.Fundamental;

import app.SourceCode.DataStructure.Trie;
import app.SourceCode.FileActivities.ReadFromFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {

    public boolean cnt = false;
    /**
     * insert words from commandline into trie.
     */
    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        int numWord = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numWord; i++) {
            String word_target = scanner.nextLine();
            String word_explain = scanner.nextLine();
            Word word = new Word(word_target, word_explain);
            Dictionary.getTrie().insert(word);
        }
        scanner.close();
    }

    /**
     * insert from txt file.
     *
     * @param filename String
     */
    public void insertFromFile_txt(String filename) {
        List<Word> Words = ReadFromFile.readTXT(filename);
        Dictionary.getTrie().insert(Words);
    }

    public void insertFromFile_sql() {
        List<Word> Words = ReadFromFile.readSQL();
        Dictionary.getTrie().insert(Words);
    }

    public List<Word> dictionaryLookup(String word) {
        return Dictionary.getTrie().search(word);
    }


    public void addWord(Word word) {
        Dictionary.getTrie().insert(word);
    }

    public void addTrie(Trie otherTrie) {
        Dictionary.getTrie().merge(otherTrie);
    }

    public void editWord(Word oldWord, Word newWord) {
        Dictionary.getTrie().edit(oldWord, newWord);
    }

    public void removeWord(Word word) {
        Dictionary.getTrie().delete(word);
    }
}
