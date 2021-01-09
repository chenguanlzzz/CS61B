public class OffByN {
    private int offset;

    public OffByN(int i) {
        offset = i;
    }
    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == offset) {
            return true;
        }
        return false;
    }
}
