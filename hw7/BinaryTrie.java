import java.io.Serializable;
import java.util.*;


/**
 * Created by Armstrong on 5/20/17.
 */
public class BinaryTrie implements Serializable {

    Node root;

    private class Node implements Comparable<Node>, Serializable{
        int size;
        Node zeroChild;
        Node oneChild;
        char c;


        public Node(char c, int size) {
            this.c = c;
            this.size = size;
        }

        public Node(Node zeroChild, Node oneChild) {
            this.zeroChild = zeroChild;
            this.oneChild = oneChild;
            size = zeroChild.size+oneChild.size;
        }

        public boolean isLeaf() {
            return zeroChild == null && oneChild == null;
        }

        public char getChar() {
            return c;
        }

        @Override
        public int compareTo(Node n) {
            return this.size - n.size;
        }

    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {

        PriorityQueue<Node> nodes = new PriorityQueue<>();

        for (Character c: frequencyTable.keySet()) {
            int size = frequencyTable.get(c);
            nodes.add(new Node(c, size));
        }

        while (nodes.size() != 1) {
            Node zeroChild = nodes.poll();
            Node oneChild = nodes.poll();
            Node combined = new Node(zeroChild, oneChild);
            nodes.add(combined);
        }

        root = nodes.poll();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {

        BitSequence prefix = new BitSequence();
        int i = 0;
        Node pointer = root;
        while (!pointer.isLeaf()) {
            int dir = querySequence.bitAt(i);
            prefix = prefix.appended(dir);
            if (dir == 0) {
                pointer = pointer.zeroChild;
            } else {
                pointer = pointer.oneChild;
            }
            i += 1;
        }
        char c = pointer.getChar();

        return new Match(prefix, c);

    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> table = new HashMap<>();

        class nodeWithKey {
            Node n;
            BitSequence key;

            nodeWithKey(Node n) {
                this.n = n;
                key = new BitSequence();
            }

            nodeWithKey(nodeWithKey n, Node child, int toAppend) {
                this.n = child;
                key = n.key.appended(toAppend);
            }
        }

        Queue<nodeWithKey> fringe = new ArrayDeque<>();
        fringe.add(new nodeWithKey(root));

        while (fringe.size() != 0) {
            nodeWithKey parent = fringe.poll();
            if (parent.n.isLeaf()) {
                table.put(parent.n.c, parent.key);
            } else {
                nodeWithKey zeroChild = new nodeWithKey(parent, parent.n.zeroChild, 0);
                fringe.add(zeroChild);
                nodeWithKey oneChild = new nodeWithKey(parent, parent.n.oneChild, 1);
                fringe.add(oneChild);
            }
        }

        return table;
    }
}
