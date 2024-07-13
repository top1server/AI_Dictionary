package app.SourceCode.Fundamental;

import app.SourceCode.DataStructure.Trie;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
public class DictionaryManagement extends Dictionary {

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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    String word_target = parts[0].trim();
                    String word_explain = parts[1].trim();

                    Word word = new Word(word_target, word_explain);
                    Dictionary.getTrie().insert(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertFromFile_sql(String filename) {

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
