/**
 * Created by Armstrong on 4/11/17.
 */
public class OffByN implements CharacterComparator{

    public int diff;

    public OffByN (int N){
        diff = N;
    }

    public boolean equalChars (char x, char y) {
        if (x-y == diff || y-x == diff) {
            return true;
        }
        return false;
    }
}
