package app.SourceCode;

import app.DataStructure.*;

public class Dictionary {
    Trie trie = new Trie();

    public void add(Word word) {
        trie.insert(word);
    }

    public void addTrieFromTrie(Trie otherTrie) {
        this.trie.merge(otherTrie);
    }

    public Trie getTrie() {
        return trie;
    }

    public void setTrie(Trie trie) {
        this.trie = trie;
    }

}
