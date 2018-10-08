import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsCount {


    private HashMap<String, Integer> map = new HashMap<>();
    private int sum = 0;


    /**
     * @return the sum of words
     */
    public int getSum() {
        return sum;
    }

    
    /**
     * @return the HashMap contain words and words's sum
     */
    public HashMap<String, Integer> getMap() {
        return map;
    }


    /**
     * @param content the input
     */
    public WordsCount(String content) {

        String[] temp = content.split("[\\s+\\p{Punct}]+");
        String countRegex = "^[a-zA-Z]{4,}.*";
        for (String i : temp) {
            if (i.matches(countRegex)) {
                sum++;
                String lowCase = i.toLowerCase();
                if (!map.containsKey(lowCase)) {
                    map.put(lowCase, 1);
                } else {
                    int num = map.get(lowCase);
                    map.put(lowCase, num + 1);
                }
            }
        }
    }



    public WordsCount(HandleContent handleContent, int m) {
        HashMap<String, Integer> titlesMap = countContent(handleContent.getTitles(), m);
        HashMap<String, Integer> abstractsMap = countContent(handleContent.getAbstracts(), m);
        sum = getWordsSum(handleContent.getHandledContent());

    }


    private int getWordsSum(String content) {
        int sum = 0;
        String[] temp = content.split("[\\s+\\p{Punct}]+");
        String countRegex = "^[a-zA-Z]{4,}.*";
        for (String i : temp) {
            if (i.matches(countRegex)) {
                sum++;
                String lowCase = i.toLowerCase();
                if (!map.containsKey(lowCase)) {
                    map.put(lowCase, 1);
                } else {
                    int num = map.get(lowCase);
                    map.put(lowCase, num + 1);
                }
            }
        }
        return sum;
    }


    private HashMap<String, Integer> countContent(List<String> contents, int m) {
        HashMap<String, Integer> map = new HashMap<>();
        String splitRegex = "[\\s+\\p{Punct}]+";
        String splitStartRegex = "^[\\s+\\p{Punct}]+";
        String wordRegex = "^[a-zA-Z]{4,}.*";
        Pattern pattern = Pattern.compile(splitRegex);

        for (String content : contents) {
            String[] temp = content.split(splitRegex);

            List<String> splits = new ArrayList<>();
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                splits.add(matcher.group());
            }

            boolean isSplitStart = content.matches(splitStartRegex);

            for (int i = 0; i < temp.length - m + 1; i++) {
                StringBuilder stringBuilder = new StringBuilder();

                if (temp[i].matches(wordRegex)) {
                    stringBuilder.append(temp[i]);
                } else {
                    break;
                }
                boolean isContinue = true;
                for (int j = 1; j < m; j++) {
                    if (!temp[i + j].matches(wordRegex)) {
                        isContinue = false;
                        break;
                    } else {
                        if (isSplitStart) {
                            stringBuilder.append(splits.get(i + j));
                        } else {
                            stringBuilder.append(splits.get(i + j - 1));
                        }
                        stringBuilder.append(temp[i + j]);
                    }
                }

                if (isContinue) {
                    String words = stringBuilder.toString().toLowerCase();
                    if (!map.containsKey(words)) {
                        map.put(words, 1);
                    } else {
                        int num = map.get(words);
                        map.put(words, num + 1);
                    }
                }

            }

        }
        return map;
    }

}
