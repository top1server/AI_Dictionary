package app.SourceCode.DataStructure;

import app.SourceCode.Fundamental.Word;

import java.util.*;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /**
     * insert into trie.
     *
     * @param word Word
     */
    public void insert(Word word) {
        TrieNode node = root;
        String target = word.getWord_target();

        for (char ch : target.toCharArray()) {
            if ((ch < 'a' || ch > 'z') && ch != ' ') {
                continue;
            }
            if (node.getChild(ch) == null) {
                node.setChild(ch, new TrieNode());
            }
            node = node.getChild(ch);
        }

        node.addWord(word);
        node.setEndOfWord(true);
    }

    /**
     * insert from ArrayList method.
     * @param wordList List
     */
    public void insert(List<Word> wordList) {
        for (Word word: wordList) {
            insert(word);
        }
    }


    /**
     * search words have String prefix from trie method.
     *
     * @param prefix String
     * @return list words
     */
    public List<Word> search(String prefix) {
        TrieNode node = root;
        List<Word> results = new ArrayList<>();

        for (char ch : prefix.toCharArray()) {
            //index_child_node = ch - 'a';
            if (node.getChild(ch) == null) {
                return results;
            }
            node = node.getChild(ch);
        }

        collectWords(node, results);
        return results;
    }

    /**
     * collect word from trie method.
     *
     * @param node    TrieNode
     * @param results List word
     */
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

    /**
     * delete word from trie method.
     *
     * @param word Word.
     */
    public void delete(Word word) {
        delete(root, word.getWord_target(), 0);
    }

    /**
     * main delete method.
     *
     * @param current TrieNode
     * @param word    Word
     * @param index   int
     * @return Trie were deleted word.
     */
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
            // Check if trie has any remaining child nodes
            return current.getWords().isEmpty() && Arrays.stream(current.children).allMatch(Objects::isNull);
        }
        return false;
    }

    /**
     * Edit word in trie by replacing old word with new word.
     *
     * @param oldWord Word to be replaced.
     * @param newWord New word to replace the old word.
     */
    public void edit(Word oldWord, Word newWord) {
        // First, delete the old word
        delete(oldWord);
        // Then, insert the new word
        insert(newWord);
    }

    /**
     * Merge another trie into this trie.
     *
     * @param otherTrie Trie to be merged.
     */
    public void merge(Trie otherTrie) {
        mergeTrieNodes(this.root, otherTrie.root);
    }

    private void mergeTrieNodes(TrieNode node1, TrieNode node2) {
        if (node2.isEndOfWord()) {
            node1.setEndOfWord(true);
            node1.getWords().addAll(node2.getWords());
        }

        for (char ch = 'a'; ch <= 'z'; ch++) {
            TrieNode childNode2 = node2.getChild(ch);
            if (childNode2 != null) {
                if (node1.getChild(ch) == null) {
                    node1.setChild(ch, new TrieNode());
                }
                mergeTrieNodes(node1.getChild(ch), childNode2);
            }
        }
    }

    /**
     * print all word in trie.
     *
     * @return list all words in the trie.
     */
    public List<Word> printAllWords() {
        List<Word> words = new ArrayList<>();
        collectWords(root, words);
        return words;
    }

}

