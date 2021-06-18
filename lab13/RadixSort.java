/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        //sortHelperLSD(asciis);
        MSD(asciis);
        return asciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     *               //@param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis) {
        // Optional LSD helper method for required LSD radix sort
        int max = 0;
        for (String str : asciis) {
            max = max > str.length() ? max : str.length();
        }

        String[] pad0asciis = padZero(asciis, max);
        for (int i = max - 1; i >= 0; i--) {
            String[] sortedByIndexAscii = new String[pad0asciis.length];
            int[] asciiNum = new int[256];
            int[] asciiStart = new int[256];
            for (String str : pad0asciis) {
                asciiNum[str.charAt(i)] += 1;
            }
            for (int j = 1; j < 256; j++) {
                asciiStart[j] = asciiStart[j - 1] + asciiNum[j - 1];
            }
            for (int k = 0; k < pad0asciis.length; k++) {
                String str = pad0asciis[k];
                char charAtIndex = str.charAt(i);
                sortedByIndexAscii[asciiStart[charAtIndex]] = str;
                asciiStart[charAtIndex] += 1;
            }
            pad0asciis = sortedByIndexAscii;
        }
        for (int i = 0; i < pad0asciis.length; i++) {
            asciis[i] = pad0asciis[i];
        }
    }

    // pading space for string length less than max length
    private static String[] padZero(String[] asciis, int max) {
        String[] padStr = new String[asciis.length];
        int j = 0;
        for (String str : asciis) {
            StringBuilder newStr = new StringBuilder();
            newStr.append(str);
            int addNum = max - str.length();
            for (int i = 0; i < addNum; i++) {
                newStr.append(" ");
            }
            padStr[j] = newStr.toString();
            j++;
        }
        return padStr;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        int[] counts = new int[256];
        int[] starts = new int[256];
        if (end <= start) {
            return;
        }
        String[] aux = new String[end - start + 1];
        for (int i = start; i <= end; i++) {
            counts[asciis[i].charAt(index)] += 1;
        }
        for (int i = 1; i < 256; i++) {
            starts[i] = starts[i - 1] + counts[i - 1];
        }

        for (int j = 0; j < aux.length; j++) {
            String str = asciis[start + j];
            char charAtIndex = str.charAt(index);
            aux[starts[charAtIndex]] = str;
            starts[charAtIndex] += 1;
        }

        for (int i = start; i <= end; i++) {
            asciis[i] = aux[i - start];
        }

        for (int r = 0; r < 255; r++) {
            sortHelperMSD(asciis, start + starts[r], start + starts[r + 1] - 1, index + 1);
        }

    }

    private static void MSD(String[] asciis) {
        int max = 0;
        for (String str : asciis) {
            max = max > str.length() ? max : str.length();
        }

        String[] asciis2 = padZero(asciis, max);
        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = asciis2[i];
        }
        sortHelperMSD(asciis, 0, asciis.length - 1, 0);
    }

    public static void main(String[] args) {
        String[] x = {"xyx", "abs", "a", "anxth", "bitch"};
        sort(x);
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
        }
    }
}
