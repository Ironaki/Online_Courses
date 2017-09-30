import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Armstrong on 5/31/17.
 */
public class Permutation {

    public static void main(String[] args) {
        int itemNum = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQ = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            rQ.enqueue(StdIn.readString());
        }

        for (int i = 0; i < itemNum; i++) {
            System.out.println(rQ.dequeue());
        }
    }
}
