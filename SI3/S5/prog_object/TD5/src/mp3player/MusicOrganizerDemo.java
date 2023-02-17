package mp3player;

public class MusicOrganizerDemo {

    public static void main(String[] args) {
        MusicOrganizer mo = new MusicOrganizer(System.getProperty("user.dir") + "/resources/audio/");
        final String song = "BigBillBroonzy-BabyPleaseDontGo1.mp3";
        mo.addTrack(song);
        mo.playSample(song);
    }
}
