import java.util.Random;

public class QCM {
    /*
    public static void main(String[] args) {
        Person p1 = new Student();
        Person p2 = new PhDStudent();
        // PhDStudent phd1 = new Student();
        // Teacher t1 = new Person();
        Student s1 = new PhDStudent();
        // s1 = p1;
        // s1 = p2;
        p1 = s1;
        // Teacher t1 = s1;
        s1 = phd1;
        phd1 = s1;
        Object o = new Student();
        s1 = o;
    }
    static class Person {
    }

    static class Student extends Person {
    }

    static class Teacher extends Person {
    }

    static class PhDStudent extends Student {
    }

 */
/*
    public static void main(String[] args) {
        Artefact aa = new Artefact();
        Artefact ac = new Cercle();
        Shape s = new Cercle();
        Cercle c = new Cercle();

        c.draw();

        s.draw();

        aa.draw();

        ac.draw();
    }

    public static class Artefact {
    }

    public static abstract class Shape extends Artefact {
        public abstract void draw();

    }

    public static class Cercle extends Shape {
        @Override
        public void draw() {
            System.out.println("I draw this Circle");
        }

    }
    */

    public interface Playable {
        void playRound();
    }

    public static class GuessingGame extends Game implements Playable {
        private final int numberToGuess;
        private final GuessingGameIO io;

        public GuessingGame(GuessingGameIO io) {
            // Generate the number to guess
            Random random = new Random();
            numberToGuess = random.nextInt(100) + 1;
            this.io = io;
        }

        public GuessingGameIO getIo() {
            return io;
        }

        @Override
        public void playRound() {
            boolean guessCorrect = false;
            while (!guessCorrect) {
                // Ask for the guess
                int guess = io.askForGuess();

                // Check the guess
                if (guess == numberToGuess) {
                    io.displayMessage("Congratulations, you guessed the correct number!");
                    guessCorrect = true;
                } else if (guess < numberToGuess) {
                    io.displayMessage("The number to guess is larger.");
                } else {
                    io.displayMessage("The number to guess is smaller.");
                }
            }
        }
    }

    private static class Game {
    }

    private static class GuessingGameIO {
        public int askForGuess() {
            return 0;
        }

        public void displayMessage(String s) {
        }
    }
}
