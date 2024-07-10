public class Hash {
    static long compute_hash(String s) {
        final int p = 31;
        final int m = 1000000009;

        long hash_value = 0;
        long p_pow = 1;

        for(char c: s.toCharArray()) {
            hash_value = (hash_value + (c - 'a' + 1) * p_pow) % m;
            p_pow = (p_pow * p) % m;
        }

        return hash_value;
    }
}
