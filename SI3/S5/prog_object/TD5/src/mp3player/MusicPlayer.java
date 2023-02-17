package mp3player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Provide basic playing of MP3 files via the javazoom library.
 * See http://www.javazoom.net/
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class MusicPlayer {
    // The current player. It might be null.
    private AdvancedPlayer player;

    /**
     * Constructor for objects of class MusicFilePlayer
     */
    public MusicPlayer() {
        player = null;
    }

    /**
     * Play a part of the given file.
     * The method returns once it has finished playing.
     *
     * @param filename The file to be played.
     */
    public void playSample(String filename) throws NonAvailableFileException {
        try {
            setupPlayer(filename);
            player.play(200);
        } catch (JavaLayerException e) {
            reportProblem(filename, e);
            throw new NonAvailableFileException("Error playing Sample " + filename, e);
        } finally {
            killPlayer();
        }
    }

    /**
     * Start playing the given audio file.
     * The method returns once the playing has been started.
     *
     * @param filename The file to be played.
     */
    public void startPlaying(final String filename) throws NonAvailableFileException {
        setupPlayer(filename);
        try {
            Thread playerThread = new Thread() {
                @Override
                public void run() {
                    try {
                        player.play(5000);
                    } catch (JavaLayerException e) {
                        reportProblem(filename, e);
                    } finally {
                        killPlayer();
                    }
                }
            };
            playerThread.start();
        } catch (Throwable e){
            throw new NonAvailableFileException("Error playing " + filename, e);
        }
    }


    public void stop() {
        killPlayer();
    }

    /**
     * Set up the player ready to play the given file.
     *
     * @param filename The name of the file to play.
     */
    private void setupPlayer(String filename) throws NonAvailableFileException {
        try {
            InputStream is = getInputStream(filename);
            player = new AdvancedPlayer(is, createAudioDevice());
        } catch (IOException|JavaLayerException e) {
            reportProblem(filename, e);
            killPlayer();
            throw new NonAvailableFileException(filename, e);
        }
    }

    /**
     * Return an InputStream for the given file.
     *
     * @param filename The file to be opened.
     * @return An input stream for the file.
     * @throws IOException If the file cannot be opened.
     */
    private InputStream getInputStream(String filename)
            throws IOException {
        return new BufferedInputStream(
                new FileInputStream(filename));
    }

    /**
     * Create an audio device.
     *
     * @return An audio device.
     * @throws JavaLayerException if the device cannot be created.
     */
    private AudioDevice createAudioDevice()
            throws JavaLayerException {
        return FactoryRegistry.systemRegistry().createAudioDevice();
    }

    /**
     * Terminate the player, if there is one.
     */
    private void killPlayer() {
        synchronized (this) {
            if (player != null) {
                player.stop();
                player = null;
            }
        }
    }

    /**
     * Report a problem playing the given file.
     * We don't want to mix error messages with the code logic
     * @param filename The file being played.
     */
    private void reportProblem(String filename, Exception e) {
        //System.out.println("There was a problem playing: " + filename);
        //System.out.println("It triggers : " + e);
        //throw new mp3player.NonAvailableFileException(filename,e);
    }

}
