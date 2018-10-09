import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HandleContentTest {
    String path = "input.txt";
    HandleContent handleContent = new HandleContent(path);

    private List<String> titles = new ArrayList<>();
    private List<String> abstracts = new ArrayList<>();

    private String handledContent;

    @Test
    public void getTitles() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
                if (line.startsWith("Title:")) {
                    titles.add(line.substring(7));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File do not exist.");
        }

        System.out.println(handleContent.getTitles().toString().equals(titles.toString()));
    }

    @Test
    public void getAbstracts() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
                if (line.startsWith("Abstract:")) {
                    abstracts.add(line.substring(10));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File do not exist.");
        }

        System.out.println(handleContent.getAbstracts().toString().equals(abstracts.toString()));
    }

    @Test
    public void getHandledContent() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
                if (line.startsWith("Title:")) {
                    titles.add(line.substring(7));
                } else if (line.startsWith("Abstract:")) {
                    abstracts.add(line.substring(10));
                }
            }
            String content = stringBuilder.toString();
            handledContent = content.replace("\r", "");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File do not exist.");
        }

        System.out.println(handleContent.getHandledContent().equals(handledContent));
    }
}