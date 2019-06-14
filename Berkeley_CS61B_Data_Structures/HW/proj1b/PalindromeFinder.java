/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("words.txt");

        while (!in.isEmpty()) {
            String word = in.readString();
            OffByN diff = new OffByN(15);
            if (word.length() >= minLength && Palindrome.isPalindrome(word, diff)) {
                System.out.println(word);
            }
        }
    }
} 
