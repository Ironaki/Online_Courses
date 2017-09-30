/**
 * Created by Armstrong on 4/11/17.
 */
public class Palindrome{

    public static Deque<Character> wordToDeque (String word) {
        Deque<Character> test = new ArrayDeque<Character>();

        for (int i = 0; i < word.length(); i += 1) {
            Character toAdd = word.charAt(i);
            test.addLast(toAdd);
        }

        return test;
    }

    public static boolean isPalindrome (String word) {
        Deque<Character> test = wordToDeque(word);
        boolean ans = true;

        while (test.size() != 0 && test.size() != 1) {
          ans = (test.removeFirst() == test.removeLast()) && ans;
        }

        return ans;
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> test = wordToDeque(word);
        boolean ans = true;

        while (test.size() != 0 && test.size() != 1) {
            char first = test.removeFirst();
            char last = test.removeLast();
            ans = cc.equalChars(first, last) && ans;
        }

        return ans;
    }

    public static void main (String[] args){
        OffByOne a = new OffByOne();
        OffByN diff2 = new OffByN(2);
        System.out.println(isPalindrome("", a));
        System.out.println(isPalindrome("baac"));
        System.out.println(isPalindrome("ac", a));
        System.out.println(isPalindrome("adefc", diff2));
        System.out.println(isPalindrome("adeefc", diff2));
        OffByN offby5 = new OffByN(5);
        System.out.println(offby5.equalChars('a', 'f'));  // true
        System.out.println(offby5.equalChars('f', 'h')); // false
    }
}
