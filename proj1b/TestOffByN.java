import org.junit.Test;
import static org.junit.Assert.*;
public class TestOffByN {

    @Test
    public void testEqualChars() {
        OffByN offBy5 = new OffByN(5);
        assertTrue(offBy5.equalChars('a', 'f'));
        assertFalse(offBy5.equalChars('f', 'h'));
        OffByN offBy2 = new OffByN(2);
        assertTrue(offBy2.equalChars('c','a'));
        assertFalse(offBy2.equalChars('c', 'b'));
    }

}
