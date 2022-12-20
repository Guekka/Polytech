package mp3player;

import javafx.util.Pair;
import utils.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MusicBank {
    private final String songsDir;
    private Map<String, List<Song>> songsByGenre;

    public MusicBank(String songsDir) {
        this.songsDir = songsDir;
        parseDir();
    }

    private void parseDir() {
        try (var files = Files.walk(Paths.get(songsDir))) {
            songsByGenre = files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.endsWith(".mp3"))
                    .map(path -> new Pair<>(Utils.pathStem(path.getParent().getFileName()), Song.of(path)))
                    .filter(pair -> pair.getValue().isPresent())
                    .map(pair -> new Pair<>(pair.getKey(), pair.getValue().get()))
                    .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())));
        } catch (java.io.IOException e) {
            System.err.println(e.toString());
        }
    }

    public List<Song> getSongsByGenre(String genre) {
        return songsByGenre.get(genre);
    }

    public List<Song> getAllSongs() {
        return songsByGenre.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public List<Song> getSongsByArtist(String artist) {
        return getAllSongs().stream().filter(song -> song.getArtist().equals(artist)).collect(Collectors.toList());
    }

    public List<Song> getSongsByTitle(String title) {
        return getAllSongs().stream().filter(song -> song.getTitle().equals(title)).collect(Collectors.toList());
    }
}