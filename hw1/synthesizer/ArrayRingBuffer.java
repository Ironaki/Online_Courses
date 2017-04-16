package synthesizer;

import java.util.Iterator;

//Make sure to make this class and all of its methods public
//Make sure to make this class extend AbstractBoundedQueue<t>

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> implements Iterable<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        //       Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[this.capacity];
    }


    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // Enqueue the item. Don't forget to increase fillCount and update last.
        // If there is no room, throw exception.
        if (fillCount >= capacity) {
            throw new RuntimeException("Ring Buffer Overflow");
        }

        rb[last] = x;
        fillCount += 1;
        last += 1;

        if (last >= capacity){
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // Dequeue the first item. Don't forget to decrease fillCount and update
        // If there is no room, throw exception.
        if (fillCount  <= 0) {
            throw new RuntimeException("Ring Buffer Underflow" );
        }

        T ans = rb[first];
        rb[first] = null;
        fillCount -= 1;
        first += 1;

        if (first >= capacity) {
            first = 0;
        }
        return ans;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // Return the first item. None of your instance variables should change.
        T ans = rb[first];
        return ans;
    }

    // When you get to part 5, implement the needed code to support iteration.
    public Iterator<T> iterator() {
        return new indexIterator();
    }

    public class indexIterator implements Iterator<T> {

        private int pointer = first;

        public boolean hasNext() {
            return pointer != last;
        }

        public T next() {
            T current = rb[pointer];
            pointer += 1;
            if (pointer >= capacity) {
                pointer = 0;
            }
            return current;
        }
    }
}
