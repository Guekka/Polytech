import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnglishScrabbleUtil {
    static final int[] scrabbleENScore = {
            // a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
            1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
    };

    private static final int[] scrabbleENDistrib = {
            // a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
            9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
    };

    private static ArrayList<String> authorizedWords;

    public static void loadDictionary() throws IOException {
        String fileName = "data/ospd.txt";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            authorizedWords = stream.map(String::trim).collect(Collectors.toCollection(ArrayList::new));
        }
    }

    public static boolean isWordAvailable(String word) {
        int[] letters = new int[26];
        word.toLowerCase().chars().forEach(letter -> letters[letter - 'a']++);
        for (int i = 0; i < 26; i++)
            if (letters[i] > scrabbleENDistrib[i])
                return false;
        return true;
    }

    public static boolean isWordAllowed(String word) {
        return authorizedWords.contains(word.toLowerCase());
    }

    public static int computeScoreOfWord(String word) {
        if (word == null || word.isEmpty() || word.isBlank())
            return 0;
        return word.toLowerCase().chars().map(letter -> scrabbleENScore[letter - 'a']).sum();
    }

    public static void main(String[] args) throws IOException {
        EnglishScrabbleUtil englishScrabbleUtil = new EnglishScrabbleUtil();
    }
}
