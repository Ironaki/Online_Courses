import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by Armstrong on 5/31/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int pointer;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[16];
        pointer = 0;
        size = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];

        int index = 0;
        for (int i = 0; i < pointer; i++) {
            if (items[i] != null) {
                newItems[index] = items[i];
                index += 1;
            }
        }

        items = newItems;
        pointer = size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (pointer == items.length) {
            double rate = ((double) size)/pointer;
            if (rate > 0.75) {
                resize(items.length*2);
            } else {
                resize(items.length);
            }
        }

        items[pointer] = item;
        size += 1;
        pointer += 1;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        while (true) {
            int index = StdRandom.uniform(pointer);
            Item res = items[index];
            if (res != null) {
                items[index] = null;
                size -= 1;
                resizeByHalf();
                return res;
            }
        }
    }

    private void resizeByHalf() {
        if (items.length <= 16) {
            return;
        }
        double rate = ((double) size)/items.length;
        if (rate < 0.25) {
            resize(items.length/2);
        }
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        while (true) {
            int index = StdRandom.uniform(pointer);
            Item res = items[index];
            if (res != null) {
                return res;
            }
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RqIterator();
    }

    private class RqIterator implements Iterator<Item> {

        int pointer = 0;
        int[] randomIndex = new int[size];

        public RqIterator() {
            resize(items.length);
            for (int i = 0; i < size; i++) {
                randomIndex[i] = i;
            }
            StdRandom.shuffle(randomIndex);
        }

        @Override
        public boolean hasNext() {
            return pointer < size;
        }

        @Override
        public Item next() {
            if (pointer == size) {
                throw new NoSuchElementException();
            }
            Item res = items[randomIndex[pointer]];
            pointer += 1;
            return res;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

        RandomizedQueue<Integer> randomQ = new RandomizedQueue<>();

        System.out.println("Expected true, and the value is: " + randomQ.isEmpty());
        for (int i = 0; i < 100; i++) {
            randomQ.enqueue(StdRandom.uniform(100));
        }
        System.out.println("Expected false, and the value is: " + randomQ.isEmpty());
        System.out.println("Expected 100, and the value is: " + randomQ.size());
        System.out.println(randomQ.sample());
        System.out.println(randomQ.sample());
        System.out.println(randomQ.sample());
        for (int i = 0; i < 92; i++) {
            randomQ.dequeue();
        }
        for (int i: randomQ) {
            System.out.print(i + " ");
        }
        for (int i = 0; i < 8; i++) {
            randomQ.dequeue();
        }
        System.out.println("");
        System.out.println("Expected true, and the value is: " + randomQ.isEmpty());
    }
}

