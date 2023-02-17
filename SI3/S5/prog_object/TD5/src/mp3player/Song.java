package mp3player;

import utils.Utils;

import java.nio.file.Path;
import java.util.Optional;

public class Song {
    private final Path filePath;
    private final String title;
    private final String artist;

    public Song(Path filePath) throws NonAvailableFileException {
        // check if file exist
        if(!filePath.toFile().exists()) {
            throw new NonAvailableFileException("File " + filePath + " does not exist");
        }

        this.filePath = filePath;

        // FEAT1: parse artist and title
        String[] parts = Utils.pathStem(this.filePath).split("-");
        if(parts.length != 2) {
            this.artist = "Unknown";
            this.title = "Unknown";
        } else {
            this.artist = Utils.parseCamelCase(parts[0]);
            this.title = Utils.parseCamelCase(parts[1]);
        }
    }

    public static Optional<Song> of(Path filePath) {
        try {
            return Optional.of(new Song(filePath));
        } catch (NonAvailableFileException e) {
            return Optional.empty();
        }
    }

    public Path getFilepath() {
        return filePath;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}
