package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
//    @Test
//    public void someTest() {
//        ArrayRingBuffer arb = new ArrayRingBuffer(10);
//    }
//
//    @Test
//    public void enqueueDequeue() {
//        ArrayRingBuffer<Integer> test = new ArrayRingBuffer<>(8);
//        test.enqueue(3);
//        test.enqueue(4);
//        test.enqueue(5);
////        for(int i: test){
////            System.out.println(i);
////        }
//    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
//        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
        ArrayRingBuffer<Integer> test = new ArrayRingBuffer<>(4);
        System.out.println(test.capacity());
        System.out.println(test.isEmpty());
        test.enqueue(3);
        System.out.println(test.isEmpty());
        test.enqueue(4);
        System.out.println(test.fillCount());
        test.enqueue(5);
        System.out.println(test.isFull());
        test.enqueue(6);
        System.out.println(test.isFull());
        test.dequeue();
        test.dequeue();
        for(int i: test){
            System.out.print(i);
        }
        System.out.println("");

        System.out.println(test.peek());
        System.out.println("The expected output: 4, true, false, 2, false, true, 56, 5");
    }

} 
