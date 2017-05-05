package lab9;
import edu.princeton.cs.algs4.In;

import java.util.LinkedList;

public class Why {

    node[] nodeArray;

    public static void main(String[] args) {

        Why why = new Why();
        why.tryThis();

        LinkedList<Integer>[] linkedListArray =  new LinkedList[9];
        for (int i = 0; i < linkedListArray.length; i++) {
            linkedListArray[i] = new LinkedList<>();
        }
        linkedListArray[0].add(5);


    }

    public Why() {
        nodeArray = new node[2];
    }

    public void tryThis() {
    }

    private class node {
        String key;
        int value;
        node next;

        node(String key, int value) {
            this.key = key;
            this.value = value;
            next = null;
        }

        node(String key, int value, node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

}
