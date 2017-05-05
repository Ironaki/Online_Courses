package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.LinkedList;

/**
 * Created by Armstrong on 4/29/17.
 */
public class MyHashMap<K,V> implements Map61B<K,V> {

    int size = 16;
    double loadFactor = 2.0;
    LinkedList<node>[] mainList;
    int totalSize;

    private class node {
        K key;
        V value;

        node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public MyHashMap() {
        mainList =  new LinkedList[size];
        for (int i = 0; i < mainList.length; i++) {
            mainList[i] = new LinkedList<>();
        }
    }

    public MyHashMap(int initialSize) {
        mainList = new LinkedList[initialSize];
        for (int i = 0; i < mainList.length; i++) {
            mainList[i] = new LinkedList<>();
        }
        this.size = initialSize;
    }

    public MyHashMap(int initialSize, double loadFactor) {
        mainList = new LinkedList[initialSize];
        for (int i = 0; i < mainList.length; i++) {
            mainList[i] = new LinkedList<>();
        }
        this.size = initialSize;
        this.loadFactor = loadFactor;
    }

    @Override
    public void clear() {
        mainList = new LinkedList[16];
        for (int i = 0; i < mainList.length; i++) {
            mainList[i] = new LinkedList<>();
        }
        size = 16;
        totalSize = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int hashIndex = Math.abs(key.hashCode()%size);
        for (node singleNode: mainList[hashIndex]) {
            if (singleNode.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int hashIndex = Math.abs(key.hashCode()%size);
        for (node singleNode: mainList[hashIndex]) {
            if (singleNode.key.equals(key)) {
                return singleNode.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return totalSize;
    }

    public void resize() {
        LinkedList<node>[] newMainList = new LinkedList[size*2];
        for (int i = 0; i < newMainList.length; i++) {
            newMainList[i] = new LinkedList<>();
        }
        size = size*2;
        for (LinkedList<node> singleList: mainList) {
            for (node singleNode: singleList) {
                int hashIndex = Math.abs(singleNode.key.hashCode()%size);
                newMainList[hashIndex].addFirst(new node(singleNode.key, singleNode.value));
            }
        }
        mainList = newMainList;
    }

    @Override
    public void put(K key, V value) {
        if (size()/(double) size > loadFactor) {
            resize();
        }
        if (!containsKey(key)) {
            totalSize += 1;
        }
        int hashIndex = Math.abs(key.hashCode()%size);
        mainList[hashIndex].addFirst(new node(key, value));
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (LinkedList<node> singleList: mainList) {
            for (node singleNode: singleList) {
                keySet.add(singleNode.key);
            }
        }
        return keySet;
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
        return null;
    }
}
