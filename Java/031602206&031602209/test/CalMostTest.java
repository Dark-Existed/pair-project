import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CalMostTest {
    String path = "input.txt";
    HandleContent handleContent = new HandleContent(path);
    WordsCount wordsCount = new WordsCount(handleContent, 3, 0);
    HashMap<String, Integer> map = wordsCount.getMap();

    CalMost calMost = new CalMost();

    @Test
    public void mostWords() {

        List<Map.Entry<String, Integer>> list = calMost.mostWords(map);
        list.forEach(System.out::println);
        System.out.println("mostWords test");
    }

    @Test
    public void mostWords1() {

        List<Map.Entry<String, Integer>> list = calMost.mostWords(map,12);
        list.forEach(System.out::println);
        System.out.println("mostWords1 test");
    }
}