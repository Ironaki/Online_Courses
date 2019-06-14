package lab8;

import edu.princeton.cs.algs4.BST;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Armstrong on 4/25/17.
 */

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private BSTNode root;

    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;
        int size;

        BSTNode (K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public K getRootKey () {
        return root.key;
    }
    @Override
    public void clear() {
        root = null;
    }

    private BSTNode searchForNode (BSTNode root, K key) {
        if (root == null) {
            return null;
        }

        int cmp = root.key.compareTo(key);
        if (cmp > 0) {
            return searchForNode(root.left, key);
        }
        if (cmp < 0) {
            return searchForNode(root.right, key);
        }
        return root;
    }

    @Override
    public boolean containsKey (K key) {
        if (searchForNode(root, key) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public V get (K key) {
        BSTNode aim = searchForNode(root, key);
        if (aim == null) {
            return null;
        } else {
            return aim.value;
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size (BSTNode node) {
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    @Override
    public void put (K key, V value) {
        root = putHelper(root, key, value);
    }

    private BSTNode putHelper (BSTNode node, K key, V value) {
        if (node == null) {
            return new BSTNode(key, value, 1);
        }

        int cmp = node.key.compareTo(key);
        if (cmp > 0) {
            node.left = putHelper(node.left, key, value);
        }
        if (cmp < 0) {
            node.right = putHelper(node.right, key, value);
        }

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void printInOrder() {
        printHelper(root);
    }

    private void printHelper (BSTNode node) {

        /** The commented out codes also work, but is less concise. */

//        if (node.left == null && node.right == null) {
//            System.out.print(node.key + " ");
//        }
//        if (node.left == null && node.right != null) {
//            System.out.print(node.key + " ");
//            printHelper(node.right);
//        }
//        if (node.left != null && node.right == null) {
//            printHelper(node.left);
//            System.out.print(node.key + " ");
//        }
//        if (node.left != null && node.right != null) {
//            printHelper(node.left);
//            System.out.print(node.key + " ");
//            printHelper(node.right);
//        }

        if (node == null) {
            return;
        }
        printHelper(node.left);
        System.out.print(node.key + " ");
        printHelper(node.right);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
