package app.DataStructure;

import app.SourceCode.Word;

import java.util.*;

public class WordTrie {
    private TrieNode root;

    public WordTrie() {
        root = new TrieNode();
    }

    public void insert(Word word) {
        TrieNode node = root;
        String target = word.getWord_target();

        for (char ch : target.toCharArray()) {
            int index = ch - 'a';
            if (node.getChild(ch) == null) {
                node.setChild(ch, new TrieNode());
            }
            node = node.getChild(ch);
        }

        node.addWord(word);
        node.setEndOfWord(true);
    }

    public List<Word> search(String prefix) {
        TrieNode node = root;
        List<Word> results = new ArrayList<>();

        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';
            if (node.getChild(ch) == null) {
                return results;
            }
            node = node.getChild(ch);
        }

        collectWords(node, results);
        return results;
    }

    private void collectWords(TrieNode node, List<Word> results) {
        if (node.isEndOfWord()) {
            results.addAll(node.getWords());
        }

        for (TrieNode child : node.children) {
            if (child != null) {
                collectWords(child, results);
            }
        }
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;

        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';
            if (node.getChild(ch) == null) {
                return false;
            }
            node = node.getChild(ch);
        }

        return true;
    }

    public boolean delete(Word word) {
        return delete(root, word.getWord_target(), 0);
    }

    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfWord()) {
                return false;
            }
            current.setEndOfWord(false);
            current.getWords().removeIf(w -> w.getWord_target().equals(word));
            return current.getWords().isEmpty();
        }

        char ch = word.charAt(index);
        TrieNode node = current.getChild(ch);
        if (node == null) {
            return false;
        }

        boolean shouldDeleteCurrentNode = delete(node, word, index + 1);

        if (shouldDeleteCurrentNode) {
            current.setChild(ch, null);
            // Kiểm tra xem có nút con còn lại không
            return current.getWords().isEmpty() && Arrays.stream(current.children).allMatch(Objects::isNull);
        }

        return false;
    }

    public static void main(String[] args) {
        WordTrie trie = new WordTrie();

        // Inserting words
        trie.insert(new Word("hello", "Xin chào"));
        trie.insert(new Word("help", "Giúp đỡ"));
        trie.insert(new Word("heaven", "Thiên đường"));

        // Searching for words
        List<Word> results = trie.search("he");
        System.out.println("Tìm kiếm các từ bắt đầu bằng 'he':");
        for (Word result : results) {
            System.out.println(result.getWord_target() + " - " + result.getWord_explain());
        }

        // Deleting a word
        System.out.println("Xóa từ 'hello' khỏi Trie");
        trie.delete(new Word("hello", "Xin chào"));

        // Searching again after deletion
        results = trie.search("he");
        System.out.println("Tìm kiếm lại các từ bắt đầu bằng 'he':");
        for (Word result : results) {
            System.out.println(result.getWord_target() + " - " + result.getWord_explain());
        }
    }
}

