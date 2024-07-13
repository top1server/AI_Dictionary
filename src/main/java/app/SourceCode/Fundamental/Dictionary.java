package app.SourceCode.Fundamental;

import app.SourceCode.DataStructure.*;

public class Dictionary {
    private static Trie trie = new Trie();

    public static Trie getTrie() {
        return trie;
    }

    public static void setTrie(Trie trie) {
        Dictionary.trie = trie;
    }
}
