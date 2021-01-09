public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindrome(wordDeque);
    }

    private boolean isPalindrome (Deque<Character> wordDeque) {
        if (wordDeque.size() == 1 || wordDeque.size() == 0) {
            return true;
        }
        Character last = wordDeque.removeLast();
        Character first = wordDeque.removeFirst();
        if (last == first) {
            return isPalindrome(wordDeque);
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindrome(wordDeque, cc);
    }

    private boolean isPalindrome(Deque<Character> wordDeque, CharacterComparator cc) {
        if (wordDeque.size() <= 1) {
            return true;
        }
        Character first = wordDeque.removeFirst();
        Character last = wordDeque.removeLast();
        if (cc.equalChars(first, last)) {
            return isPalindrome(wordDeque, cc);
        }
        return false;
    }
}
