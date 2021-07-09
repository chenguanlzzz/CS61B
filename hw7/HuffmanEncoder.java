import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : inputSymbols) {
            int count = freqMap.getOrDefault(ch, 0) + 1;
            freqMap.put(ch, count);
        }
        return freqMap;
    }

    public static void main(String[] args) {
        char[] input = FileUtils.readFile(args[0]);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        Map<Character, Integer> freqTable = buildFrequencyTable(input);
        BinaryTrie decodingTrie = new BinaryTrie(freqTable);
        ow.writeObject(decodingTrie);

        Map<Character, BitSequence> lookupTable = decodingTrie.buildLookupTable();
        List<BitSequence> sequences = new ArrayList<>();
        for (char ch : input) {
            sequences.add(lookupTable.get(ch));
        }

        BitSequence massiveSeq = BitSequence.assemble(sequences);
        ow.writeObject(massiveSeq);
    }
}
