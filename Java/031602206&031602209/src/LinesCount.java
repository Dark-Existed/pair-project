import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LinesCount {

    /**
    * @param filePath the file's path
    * @return the sum of lines
    */
    public int linesNumber(String filePath) {
        int sum_a = 0;
        int sum_n = 0;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches("\\d+")) {
                    sum_n++;
                } else if (line.length() != 0 && !line.matches("\\s+")) {
                    sum_a++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sum_a;
    }

}
