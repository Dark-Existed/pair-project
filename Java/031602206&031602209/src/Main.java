import java.io.*;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Parser parser;

        if (args == null || args.length == 0) {
            parser = new Parser();
        } else {
            parser = new Parser(args);
        }

        try {
            HandleContent handleContent = new HandleContent(parser.getI());

            CharsCount charsCount = new CharsCount();
            WordsCount wordsCount = new WordsCount(handleContent.getHandledContent());
            LinesCount linesCount = new LinesCount();

            int lines = linesCount.linesNumber(parser.getI());
            int characters = charsCount.charsNumber(handleContent.getHandledContent());
            int words = wordsCount.getSum();
            List<Map.Entry<String, Integer>> mostList = new CalMost().mostWords(wordsCount.getMap());

            PrintStream printStream = new PrintStream(parser.getO());
            System.setOut(printStream);

            System.out.println("characters: " + characters);
            System.out.println("words: " + words);
            System.out.println("lines: " + lines);
            for (Map.Entry<String, Integer> i : mostList) {
                System.out.println("<"+i.getKey()+">: " + i.getValue());
            }

            printStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Output to result.txt failed.");
        }

    }

}
