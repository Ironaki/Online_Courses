import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created by Armstrong on 5/31/17.
 */
public class Deque<Item> implements Iterable<Item> {

    private Item[] arrayDeque;
    private int size;
    private int pointer;

    public Deque() {
        arrayDeque = (Item[]) new Object[16];
        size = 0;
        pointer = 0;
    }

    private void resizeHelper(double rate) {
        int oldLength = arrayDeque.length;
        if (oldLength <= 16 && rate == 0.5) {
            return;
        }
        Item[] newArrayDeque = (Item[]) new Object[(int) (oldLength*rate)];
        if (pointer+size <= arrayDeque.length) {
            System.arraycopy(arrayDeque, pointer, newArrayDeque, 0, size);
            pointer = 0;
            arrayDeque = newArrayDeque;
        } else {
            System.arraycopy(arrayDeque, pointer, newArrayDeque, 0, oldLength-pointer);
            System.arraycopy(arrayDeque, 0, newArrayDeque, oldLength-pointer, size-(oldLength-pointer));
            pointer = 0;
            arrayDeque = newArrayDeque;
        }
    }

    private void resize() {
        double usageRate = ((double) size)/arrayDeque.length;
        if (size == arrayDeque.length) {
            resizeHelper(2.0);
        } else if (usageRate < 0.25) {
            resizeHelper(0.5);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }


    private void checkAdd(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    public void addFirst(Item item) {
        checkAdd(item);
        resize();
        if (pointer != 0) {
            arrayDeque[pointer-1] = item;
            pointer -=  1;
        } else {
            arrayDeque[arrayDeque.length-1] = item;
            pointer = arrayDeque.length-1;
        }
        size += 1;
    }

    public void addLast(Item item) {
        checkAdd(item);
        resize();
        if (pointer+size < arrayDeque.length) {
            arrayDeque[size+pointer] = item;
        } else {
            arrayDeque[size+pointer-arrayDeque.length] = item;
        }
        size += 1;
    }

    private void checkRemove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public Item removeFirst() {
        checkRemove();
        resize();
        Item res = arrayDeque[pointer];
        arrayDeque[pointer] = null;
        if (pointer != arrayDeque.length-1) {
            pointer += 1;
        } else {
            pointer = 0;
        }
        size -= 1;
        return res;
    }

    public Item removeLast() {
        checkRemove();
        resize();
        Item res;
        if (pointer+size > arrayDeque.length) {
            res = arrayDeque[pointer+size-arrayDeque.length-1];
            arrayDeque[pointer+size-arrayDeque.length-1] = null;
        } else {
            res = arrayDeque[pointer+size-1];
            arrayDeque[pointer+size-1] = null;
        }
        size -= 1;
        return res;
    }

    @Override
    public Iterator<Item> iterator() {
        return new dequeIterator();
    }

    private class dequeIterator implements Iterator<Item> {

        int start = pointer;
        int remain = size;

        @Override
        public boolean hasNext() {
            return remain != 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (remain == 0) {
                throw new NoSuchElementException();
            }
            Item res = arrayDeque[start];
            start += 1;
            if (start == arrayDeque.length) {
                start = 0;
            }
            remain -= 1;
            return res;
        }
    }

    public static void main(String[] args) {

        Deque<Integer> intDeque = new Deque<>();
        System.out.println(intDeque.isEmpty());
        for (int i = 0; i < 20; i++) {
            intDeque.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            intDeque.addFirst(i);
        }
        intDeque.removeFirst();
        intDeque.removeFirst();
        for (int i: intDeque) {
            System.out.print(i + " ");
        }
        System.out.println("");
        System.out.println(intDeque.size);
    }
}