package mp3player;

import java.util.Scanner;

/**
 * @author Mireille Blay-Fornarino
 */
/*
public class MusicPlayerDemo {
    private static final String RESOURCES_DIR = System.getProperty("user.dir") + "/resources/audio/";

    public static void main(String[] args) {
        MusicPlayer mp = new MusicPlayer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Placez vos fichiers audio sous : " + RESOURCES_DIR);
        boolean goOn = true;


        while (goOn) {
            String command = getCommand("What file do you want to listen to (to stop type 's')?",sc);
            goOn = !command.equals("s");
            if (goOn) {
                try {
                    mp.playSample(RESOURCES_DIR + command);
                } catch (NonAvailableFileException e) {
                    System.out.println("The file cannot be listen : " + e);

                    command = getCommand("What file do you want to listen to (to stop type 's')?", sc);
                    goOn = !command.equals("s");
                }
            }
        }

        assert !goOn;

        String command = getCommand("What file do you want to listen to for a long time (to stop type 's')?",sc);
        goOn = !command.equals("s");
        while (goOn) {
            try {
                mp.startPlaying(RESOURCES_DIR + command);
                command = getCommand("To stop to listen type (s) or a new file name ", sc);
                mp.stop();
                goOn = !command.equals("s");
            } catch (NonAvailableFileException e) {
                System.out.println("The file cannot be listen : " + e);
            }
        }
    }

    private static String getCommand(String s, Scanner sc) {
        String command;
        System.out.print(s);
        command = sc.nextLine();
        return command;
    }
}
*/
