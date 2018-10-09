import org.junit.Test;

import static org.junit.Assert.*;

public class CharsCountTest {

    @Test
    public void charsNumber() {
        assertEquals(22, new CharsCount().charsNumber("Title: Super-Resolving"));
        assertEquals(20, new CharsCount().charsNumber(", conventional face\t"));
        assertEquals(10, new CharsCount().charsNumber("123 \n jlsg"));
    }
}