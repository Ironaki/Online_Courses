/**
 * Created by Armstrong on 4/11/17.
 */
public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars (char x, char y){
        if (x-y == 1 || y-x == 1) {
            return true;
        }
        return false;
    }
}
