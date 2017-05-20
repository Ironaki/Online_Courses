/* A simple implementation of array-based trie for lower English letters*/

/**
 * Created by Armstrong on 5/19/17.
 */
public class Trie {

    private static final int R = 26;

    private Node root;
    private int size;

    public Trie() {
        root = new Node();
        size = 0;
    }

    private class Node {
        private boolean exist;
        private Node[] next;

        public Node() {
            next = new Node[R];
        }
    }

    public int getSize() {
        return size;
    }

    public void clear() {
        root.next = new Node[R];
        size = 0;
    }

    public void insert(String s) {
        Node pointer = root;
        for (int i = 0; i < s.length(); i++) {
            int index = charToIndex(s.charAt(i));
            if (index > R-1 || index < 0) {
                throw new IllegalArgumentException("This trie only support lower English letters");
            }
            if (pointer.next[index] == null) {
                pointer.next[index] = new Node();
            }
            pointer = pointer.next[index];
        }
        pointer.exist = true;
        size += 1;
    }

    private int charToIndex (char c) {
        return (int) c-97;
    }

    public boolean contains(String s) {
        Node pointer = root;
        for (int i = 0; i < s.length(); i++) {
            int index = charToIndex(s.charAt(i));
            if (index > R-1 || index < 0) {
                throw new IllegalArgumentException("This trie only support lower English letters");
            }
            if (pointer.next[index] == null) {
                return false;
            }
            pointer = pointer.next[index];
        }
        return pointer.exist;
    }

    public boolean isPrefix(String s) {
        Node pointer = root;
        for (int i = 0; i < s.length(); i++) {
            int index = charToIndex(s.charAt(i));
            if (index > R-1 || index < 0) {
                throw new IllegalArgumentException("This trie only support lower English letters");
            }
            if (pointer.next[index] == null) {
                return false;
            }
            pointer = pointer.next[index];
        }
        return true;
    }


    public static void main(String[] args) {
        Trie tEins = new Trie();
        tEins.insert("klijia");
        System.out.println(tEins.contains("kli"));
        System.out.println(tEins.isPrefix("kli"));
        System.out.println(tEins.contains("klijias"));
        System.out.println(tEins.isPrefix("klijias"));
    }
}
