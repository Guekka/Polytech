import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Shakespeare {
    private List<String> words;

    public void loadWords() throws IOException {
        String fileName = "data/shakespeare.txt";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            words = stream.map(s -> s.split("\\s")).flatMap(Stream::of).distinct().toList();
        }
    }

    
}
