package mp3player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SongTest {
    private static final String RESOURCE_PATH = System.getProperty("user.dir") + "/resources/audio/";

    private Song open(String name) {
        try {
            return new Song(RESOURCE_PATH + name);
        } catch (NonAvailableFileException e) {
            fail("The file cannot be opened : " + e);
        }
        return null;
    }

    @Test
    void testConstructor() {
        Song s = open("BigBillBroonzy-BabyPleaseDontGo1.mp3");
        assertEquals(RESOURCE_PATH + "BigBillBroonzy-BabyPleaseDontGo1.mp3", s.getFilepath());
    }

    @Test
    void testConstructorWithFileNotExisting() {
        assertThrows(NonAvailableFileException.class, ()-> new Song("unknown.mp3"));
    }

    @Test
    void testParsing() {
        Song s = open("BigBillBroonzy-BabyPleaseDontGo1.mp3");
        assertEquals("Big Bill Broonzy", s.getArtist());
        assertEquals("Baby Please Dont Go 1", s.getTitle());
    }
}
