/**
 * Created by Armstrong on 4/3/17.
 */
public class ArrayDeque<Flug> {
    public Flug[] all;
    public int size;
    public int pointer;
    public double usageRate;

    /** Initiate an empty ArrayDeque.
     * Passed */
    public ArrayDeque(){
        all = (Flug[]) new Object[8];
        size = 0;
        pointer = 0;
        usageRate = (double)size/all.length;
    }

    /** A resize method
     *  Resize method passed in all add and remove method*/
    private void resize(int capacity){
        Flug[] allResized = (Flug[]) new Object[capacity];
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
    public void addFirst(Flug i){
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
    public void addLast(Flug i){
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
    public boolean isEmpty(){
        return size == 0;
    }

    /** Passed */
    public int size(){
        return size;
    }

    /** Passed */
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
    public Flug removeFirst(){
        usageRate = (double)size/all.length;
        if (usageRate < 0.25){
            resize((int)(all.length * 0.5));
        }
        if(size == 0){
            return null;
        }
        size -= 1;
        Flug A = all[pointer];
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
    public Flug removeLast(){
        usageRate = (double)size/all.length;
        if (usageRate < 0.25){
            resize((int)(all.length * 0.5));
        }
        if (size == 0){
            return null;
        }
        Flug A;
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
    public Flug get(int index){
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
