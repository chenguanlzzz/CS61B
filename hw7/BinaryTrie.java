import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BinaryTrie implements Serializable {
    Node topNode;
    Map<Character, BitSequence> lookUpTable;
    Map<Character, Integer> frequencyTable;

    private static class Node implements Comparable<Node>, Serializable {
        char ch;
        int freq;
        Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public int compareTo(Node other) {
            return this.freq - other.freq;
        }

        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        this.frequencyTable = frequencyTable;
        MinPQ<Node> freqPQ = new MinPQ<>();
        Set<Character> charSet = frequencyTable.keySet();
        for (char ch : charSet) {
            freqPQ.insert(new Node(ch, frequencyTable.get(ch), null, null));
        }

        while (freqPQ.size() > 1) {
            Node left = freqPQ.delMin();
            Node right = freqPQ.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            freqPQ.insert(parent);
        }
        this.topNode = freqPQ.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        Node huffTrie = this.topNode;
        int i = 0;
        for (; i < querySequence.length(); i++) {
            int bit = querySequence.bitAt(i);
            if (huffTrie.isLeaf()) {
                break;
            }
            if (bit == 0) {
                huffTrie = huffTrie.left;
            } else {
                huffTrie = huffTrie.right;
            }
        }
        return new Match(querySequence.firstNBits(i), huffTrie.ch);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> codeMap = new HashMap<>();
        BitSequence bits = new BitSequence();
        this.lookUpTable = buildLookupTable(codeMap, this.topNode, bits);
        return this.lookUpTable;
    }

    private Map<Character, BitSequence> buildLookupTable(Map<Character, BitSequence> codeMap, Node node, BitSequence bits) {
        if (node.isLeaf()) {
            codeMap.put(node.ch, bits);
            return codeMap;
        }
        buildLookupTable(codeMap, node.left, bits.appended(0));
        buildLookupTable(codeMap, node.right, bits.appended(1));
        return codeMap;
    }
}
