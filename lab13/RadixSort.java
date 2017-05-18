/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra
 * @version 1.4 - April 14, 2016
 *
 **/
public class RadixSort
{

    /**
     * Does Radix sort on the passed in array with the following restrictions:
     *  The array can only have ASCII Strings (sequence of 1 byte characters)
     *  The sorting is stable and non-destructive
     *  The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     **/
    public static String[] sort(String[] asciis)
    {
        int maxLen = 0;
        for (String s: asciis) {
            if (s.length() > maxLen) {
                maxLen = s.length();
            }
        }

        String[] unsorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            unsorted[i] = asciis[i];
        }
        String[] sorted = new String[asciis.length];

        for (int digit = maxLen-1; digit >= 0; digit--) {

            int nothing = 0;
            int[] counts = new int[256];
            int[] start = new int[256];

            for (int i = 0; i < asciis.length; i++) {
                String s = unsorted[i];

                if (s.length()-1 < digit) {
                    nothing += 1;
                } else {
                    char c = s.charAt(digit);
                    counts[(int) c] += 1;
                }
            }

            for (int j = 1; j < start.length; j++) {
                start[j] = counts[j-1] + start[j-1];
            }

            for (int j = 0; j < start.length; j++) {
                start[j] = start[j] + nothing;
            }

            int k = 0;
            for (int i = 0; i < asciis.length; i++) {
                String s = unsorted[i];

                if (s.length()-1 < digit) {
                    sorted[k] = s;
                    k += 1;
                } else {
                    char c = s.charAt(digit);
                    int charInt = (int) c;
                    sorted[start[charInt]] = s;
                    start[charInt] += 1;
                }
            }
            for (int i = 0; i < unsorted.length; i++) {
                unsorted[i] = sorted[i];
            }

        }

        return sorted;
    }

    /**
     * Radix sort helper function that recursively calls itself to achieve the sorted array
     *  destructive method that changes the passed in array, asciis
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelper(String[] asciis, int start, int end, int index)
    {
        //TODO use if you want to
    }


    public static void main(String[] args) {
        String[] toSort = {"Yukino", "Asuna", "Setuna", "Asuka", "Kawori",
                "Reina", "Kurisu", "Tomoyo", "Eru", "Utaha", "Mashu", "Haruna"};

        String[] sorted = sort(toSort);

        System.out.print("The original order is: ");
        for (String s: toSort) {
            System.out.print(s);
            System.out.print(" ");
        }

        System.out.println("");

        System.out.print("The sorted order is: ");
        for (String s: sorted) {
            System.out.print(s);
            System.out.print(" ");
        }


    }
}
