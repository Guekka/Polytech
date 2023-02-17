package mp3player;

public class NonAvailableFileException extends Exception {
    public NonAvailableFileException(String filename, Throwable e) {
        super ("Error reading file " + filename, e);
    }
    public NonAvailableFileException(String message) {
        super (message);
    }
}
