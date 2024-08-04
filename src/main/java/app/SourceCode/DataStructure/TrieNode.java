package app.SourceCode.DataStructure;

import app.SourceCode.Fundamental.Word;

import java.util.*;

public class TrieNode {
    public TrieNode[] children;
    private boolean isEndOfWord;
    private final List<Word> words;    // List words can be saved at leaf note

    public TrieNode() {
        this.children = new TrieNode[27]; // 26 english's alphabet
        this.isEndOfWord = false;
        this.words = new ArrayList<>();
    }

    public TrieNode getChild(char ch) {
        if (ch == ' ') return children[26];
        return children[ch - 'a'];
    }

    public void setChild(char ch, TrieNode node) {
        if (ch == ' ') {
            children[26] = node;
        } else {
            children[ch - 'a'] = node;
        }
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public List<Word> getWords() {
        return words;
    }

    public void addWord(Word word) {
        words.add(word);
    }
}

