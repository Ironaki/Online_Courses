/**
 * Created by Armstrong on 4/11/17.
 */
public interface Deque<Item> {

    void addFirst(Item x);

    void addLast(Item x);

    boolean isEmpty();

    int size();

    void printDeque();

    Item removeFirst();

    Item removeLast();

    Item get(int x);

    Item getRecursive(int x);

}
