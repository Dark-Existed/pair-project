import org.junit.Test;

import static org.junit.Assert.*;

public class LinesCountTest {

    String path = "input.txt";

    @Test
    public void linesNumber() {
        assertEquals(3, new LinesCount().linesNumber(path));
    }
}