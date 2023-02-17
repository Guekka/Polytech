package mp3player;

import java.util.ArrayList;
import java.util.List;

/*
    Il permet d’enregistrer des références vers les fichiers, que nous nommerons piste.
    Il n’a pas de limite prédéterminée sur le nombre de pistes qu’il peut stocker.
    Il peut nous dire combien de pistes il a enregistré.
    Il peut lister les pistes qu’il a enregistrées.
    Il nous permet d’écouter une piste et de stopper son écoute.
 */
public class MusicOrganizer {
    private final MusicPlayer player;
    private final List<String> tracks;

    private final String resourceDir;

    public MusicOrganizer(String resourceDir) {
        player = new MusicPlayer();
        tracks = new ArrayList<>();
        this.resourceDir = resourceDir;
    }

    public void addTrack(String filename) {
        tracks.add(filename);
    }

    public int getNumberOfTracks() {
        return tracks.size();
    }

    public void listAllTracks() {
        for (String track : tracks) {
            System.out.println(track);
        }
    }

    public void playSample(String track) {
        try {
            player.playSample(resourceDir + track);
        } catch (NonAvailableFileException e) {
            System.out.println("The file cannot be found : " + e);
        }
    }

    public void stopPlaying() {
        player.stop();
    }
}