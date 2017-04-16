/**
 * Created by Armstrong on 4/10/17.
 */

import static org.junit.Assert.*;

import com.sun.org.apache.xpath.internal.operations.Operation;
import edu.princeton.cs.algs4.*;
import org.junit.Test;

public class TestArrayDeque1B {

    @Test
    public void TestStudentArrayDeque() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();

        OperationSequence os = new OperationSequence();

        for (int i = 0; i < 10; i += 1) {
            double randomValueBetweenZeroAndOne = StdRandom.uniform();

            if (randomValueBetweenZeroAndOne < 0.5) {
                studentDeque.addLast(i);
                solutionDeque.addLast(i);
                DequeOperation aLastOp = new DequeOperation("addLast", i);
                os.addOperation(aLastOp);
            } else {
                studentDeque.addFirst(i);
                solutionDeque.addFirst(i);
                DequeOperation aFirstOp = new DequeOperation("addFirst", i);
                os.addOperation(aFirstOp);
            }
        }

        // remove items, retrieve values and compare
        for (int i = 0; i < 5; i += 1) {
            Integer studentLast = studentDeque.removeLast();
            Integer solutionLast = solutionDeque.removeLast();
            DequeOperation rLastOp = new DequeOperation("removeLast");
            os.addOperation(rLastOp);
            assertEquals(os.toString(), solutionLast, studentLast);
        }

        for (int i = 0; i < 5; i += 1) {
            Integer studentFirst = studentDeque.removeFirst();
            Integer solutionFirst = solutionDeque.removeFirst();
            DequeOperation rFirstOp = new DequeOperation("removeFirst");
            os.addOperation(rFirstOp);
            assertEquals(os.toString(), solutionFirst, studentFirst);
        }
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestArrayDeque1B.class);
    }

}
