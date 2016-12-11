package lab3.types;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author JJ
 */
public class ArrayTypeTest {

    @Test
    public void validStringTest() throws Exception {
        String test = "\"I'm JJ\"";
        assertTrue(ArrayType.validString(test));
    }

    @Test
    public void validStringTest2() throws Exception {
        String test = "\"Testing \\\"quotes\\\" not at the end.\"";
        assertTrue(ArrayType.validString(test));
    }

    @Test
    public void validStringTest3() throws Exception {
        String test = "\"Testing ending \\\"quotes\\\"\"";
        assertTrue(ArrayType.validString(test));
    }

    @Test
    public void validStringTest4() throws Exception {
        String test = "\"Hey \\0 What's up? \\0\"";
        assertTrue(ArrayType.validString(test));
    }

    @Test
    public void validStringTest5() throws Exception {
        String test = "\"Hey \\0What's up? \\nSome new line.\"";
        assertTrue(ArrayType.validString(test));
    }

    @Test
    public void validStringTest6() throws Exception {
        String test = "\"Hey \\tWhat's up? \\nSome new line.\"";
        assertTrue(ArrayType.validString(test));
    }

    @Test
    public void invalidStringTest() throws Exception {
        // "\"
        String test = "\"\\\"";
        assertFalse(ArrayType.validString(test));
    }

    @Test
    public void invalidStringTest2() throws Exception {
        // "\x"
        String test = "\"\\x\"";
        assertFalse(ArrayType.validString(test));
    }

    @Test
    public void invalidStringTest3() throws Exception {
        // "Invalid\"
        String test = "\"Invalid\\\"";
        assertFalse(ArrayType.validString(test));
    }

}
