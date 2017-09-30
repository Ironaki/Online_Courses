import java.util.*;

/**
 * Created by Armstrong on 5/20/17.
 */
public class HuffmanDecoder {

    public static void main(String[] args) {
        ObjectReader hufFile = new ObjectReader(args[0]);
        BinaryTrie trieMain= (BinaryTrie) hufFile.readObject();
        BitSequence hugeBitSeq = (BitSequence) hufFile.readObject();
        List<Character> allChar = new ArrayList<>();
        while (hugeBitSeq.length() != 0) {
            Match m = trieMain.longestPrefixMatch(hugeBitSeq);
            allChar.add(m.getSymbol());
            hugeBitSeq = hugeBitSeq.allButFirstNBits(m.getSequence().length());
        }
        Object[] charsO = allChar.toArray();
        char[] chars = new char[charsO.length];
        for (int i = 0; i < charsO.length; i++) {
            chars[i] = (char) charsO[i];
        }
        FileUtils.writeCharArray(args[1], chars);

    }

}
