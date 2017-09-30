/**
 * Created by Armstrong on 4/3/17.
 */
public class ArrayDeque<Item> implements Deque<Item> {
    public Item[] all;
    public int size;
    public int pointer;
    public double usageRate;

    /** Initiate an empty ArrayDeque.
     * Passed */
    public ArrayDeque(){
        all = (Item[]) new Object[8];
        size = 0;
        pointer = 0;
        usageRate = (double)size/all.length;
    }

    /** A resize method
     *  Resize method passed in all add and remove method*/
    private void resize(int capacity){
        Item[] allResized = (Item[]) new Object[capacity];
        if (pointer + size > all.length){
            System.arraycopy(all, pointer, allResized,0, all.length - pointer);
            System.arraycopy(all, 0, allResized, all.length - pointer, size - (all.length - pointer));
            pointer = 0;
            all = allResized;
        }else{
            System.arraycopy(all, pointer, allResized, 0, size);
            pointer = 0;
            all = allResized;
        }
    }

    /** Passed */
    @Override
    public void addFirst(Item i){
        usageRate = (double)size/all.length;
        if (usageRate == 1){
            resize(all.length * 2);
        }
        if (pointer == 0){
            all[all.length - 1] = i;
            pointer = all.length - 1;
        }else {
            all[pointer - 1] = i;
            pointer -= 1;
        }
        size += 1;

    }

    /** Passed */
    @Override
    public void addLast(Item i){
        usageRate = (double)size/all.length;
        if (usageRate == 1){
            resize(all.length * 2);
        }
        if (size + pointer >= all.length) {
            all[size + pointer - all.length] = i;
        }else {
            all[size + pointer] = i;
        }
        size += 1;
    }

    /** Passed */
    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    /** Passed */
    @Override
    public int size(){
        return size;
    }

    /** Passed */
    @Override
    public void printDeque(){
        int position = pointer;
        int remain = size;
        while(remain > 0){
            System.out.print(all[position]);
            System.out.print(" ");
            remain -= 1;
            position += 1;
            if (position == all.length){
                position = 0;
            }
        }
    }

    /** Removes the first item and return its value.
     *  Passed */
    @Override
    public Item removeFirst(){
        usageRate = (double)size/all.length;
        if (usageRate < 0.25){
            resize((int)(all.length * 0.5));
        }
        if(size == 0){
            return null;
        }
        size -= 1;
        Item A = all[pointer];
        all[pointer] = null;
        if (pointer == all.length - 1){
            pointer = 0;
        }else{
            pointer += 1;
        }
        return A;
    }

    /** Remove the last item and return its value.
     *  Passed */
    @Override
    public Item removeLast(){
        usageRate = (double)size/all.length;
        if (usageRate < 0.25){
            resize((int)(all.length * 0.5));
        }
        if (size == 0){
            return null;
        }
        Item A;
        if (pointer + size > all.length){
            A = all[pointer + size - 1 - all.length];
            all[pointer + size - 1 - all.length] = null;
        }else{
            A = all[pointer + size - 1];
            all[pointer + size - 1] = null;
        }
        size -= 1;
        return A;
    }

    /** Return the index-th item.
     *  The first item is indexed with 0.
     *  Passed */
    @Override
    public Item get(int index){
        if (index >= size){
            return null;
        }
        if (index + pointer >= all.length){
            return all[index + pointer - all.length];
        }else{
            return all[index + pointer] ;
        }
    }

    /** The method is not recursive
     *  I need this for the code to compile, since I'm implementing an interface.
     */
    @Override
    public Item getRecursive(int index){
        if (index >= size){
            return null;
        }
        if (index + pointer >= all.length){
            return all[index + pointer - all.length];
        }else{
            return all[index + pointer] ;
        }
    }
}
