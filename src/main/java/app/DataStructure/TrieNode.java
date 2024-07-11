package app.DataStructure;

import app.SourceCode.Word;

import java.util.*;

public class TrieNode {
    public TrieNode[] children;
    private boolean isEndOfWord;
    private List<Word> words; // Danh sách các từ có thể lưu tại nút lá

    public TrieNode() {
        this.children = new TrieNode[26]; // 26 chữ cái trong bảng chữ cái tiếng Anh
        this.isEndOfWord = false;
        this.words = new ArrayList<>();
    }

    public TrieNode getChild(char ch) {
        return children[ch - 'a'];
    }

    public void setChild(char ch, TrieNode node) {
        children[ch - 'a'] = node;
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

