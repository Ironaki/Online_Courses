/**
 * Created by Armstrong on 4/2/17.
 */
public class LinkedListDeque<Flug> {

    private class Deque{
        public Flug item;
        public Deque previous;
        public Deque next;

        public Deque(Deque p, Flug i, Deque n){
            previous = p;
            item = i;
            next = n;
        }
    }

    private Deque sentinel;
    private int size;

    /** Initiate LinkedListDeque
     *  Two ways */
    public LinkedListDeque(){
        sentinel = new Deque(null, null, null);
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    }

    public LinkedListDeque(Flug i){
        Deque S = new Deque(sentinel, i, sentinel);
        sentinel = new Deque(S, null, S);
        size = 1;
    }


    /** The following methods handle Deque.*/
    public void addFirst(Flug i){
        Deque A = new Deque(sentinel, i, sentinel.next);
        sentinel.next.previous = A;
        sentinel.next = A;
        size += 1;
    }

    public void addLast(Flug i){
        Deque A = new Deque(sentinel.previous, i, sentinel);
        sentinel.previous.next = A;
        sentinel.previous = A;
        size += 1;
    }

    public boolean isEmpty(){
        return sentinel.next == sentinel;
    }


    public int size(){
        return size;
    }

    public void printDeque(){
        Deque P = sentinel;
        while (P.next != sentinel){
            P = P.next;
            System.out.print(P.item);
            System.out.print(" ");
        }
    }

    public Flug removeFirst(){
        if (sentinel.next == sentinel){
            return null;
        }
        sentinel.next = sentinel.next.next;
        Flug A = sentinel.next.previous.item;
        sentinel.next.previous = sentinel;
        size -= 1;
        return A;
    }

    public Flug removeLast(){
        if (sentinel.previous == sentinel){
            return null;
        }
        sentinel.previous = sentinel.previous.previous;
        Flug A = sentinel.previous.next.item;
        sentinel.previous.next = sentinel;
        size -= 1;
        return A;
    }

    public Flug get(int index){
        if (size - 1 < index){
            return null;
        }
        Deque P = sentinel;
        while(index > 0){
            P = P.next;
            index -= 1;
        }
        return P.next.item;
    }

    /**Well this method is copied from some answer online.
     * Using a helper function makes things easier. */

    public Flug getRecursive(int index){
        if (size - 1 < index){
            return null;
        }
        if (index == 0){
            return sentinel.next.item;
        }
        Deque pointer = sentinel.next;
        return recursionHelper(pointer, index);
    }

    private Flug recursionHelper (Deque pointer ,int index){
        if (index == 0){
            return pointer.item;
        }
        pointer = pointer.next;
        return recursionHelper(pointer, index - 1);
    }
}
