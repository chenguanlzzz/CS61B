package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(2);
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        assertEquals((Integer) 1, arb.peek());
        assertFalse(arb.isFull());
        arb.enqueue(2);
        assertTrue(arb.isFull());
        assertEquals((Integer)1, arb.dequeue());
        assertFalse(arb.isFull());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
