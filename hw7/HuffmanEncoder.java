import java.util.*;

/**
 * Created by Armstrong on 5/20/17.
 */
public class HuffmanEncoder {

    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols){
        Map<Character, Integer> res = new HashMap<>();

        for (char c: inputSymbols) {
            if (res.get(c) == null) {
                res.put(c, 1);
            } else {
                int origin = res.get(c);
                res.put(c, origin+1);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        char[] chars = FileUtils.readFile(args[0]);
        Map<Character, Integer> freq = buildFrequencyTable(chars);
        BinaryTrie trieMain = new BinaryTrie(freq);

        ObjectWriter hufFile = new ObjectWriter(args[0]+".huf");
        hufFile.writeObject(trieMain);
        Map<Character, BitSequence> table = trieMain.buildLookupTable();
        List<BitSequence> bitList = new ArrayList<>();
        for (char c: chars) {
            BitSequence toAdd = table.get(c);
            bitList.add(toAdd);
        }
        BitSequence hugeBitSeq = BitSequence.assemble(bitList);
        hufFile.writeObject(hugeBitSeq);
    }
}
