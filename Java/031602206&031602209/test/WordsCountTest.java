import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WordsCountTest {

    String path = "input.txt";
    HandleContent handleContent = new HandleContent(path);
    String Content = handleContent.getHandledContent();
    WordsCount wordsCount = new WordsCount(handleContent, 1, 0);

    @Test
    public void getSum() {
        int sum = 0;
        String[] temp = Content.split("[\\s+\\p{Punct}]+");
        String countRegex = "^[a-zA-Z]{4,}.*";
        for (String i : temp) {
            if (i.matches(countRegex)) {
                sum++;
            }
        }
        WordsCount wordsCount = new WordsCount(handleContent, 1, 0);
        assertEquals(sum, wordsCount.getSum());
    }

    @Test
    public void getMap() {
        System.out.println(wordsCount.getMap().toString());
    }
}