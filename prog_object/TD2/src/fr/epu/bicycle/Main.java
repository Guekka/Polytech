package fr.epu.bicycle;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EBike e1 = new EBike(new Battery(100));
        final String STOP = "s";

        Scanner keyboard = new Scanner(System.in);
        keyboard.useLocale(Locale.US); // To read the doubles with . and not ,

        while (true) {
            double newDistance = askForDistance(keyboard);
            e1.addKm(newDistance);
            System.out.println("\t Your bike has therefore travelled " + e1.getKm() + " km or " + e1.getKm() * 0.621371 + " miles.");
            System.out.print("Do you want to continue or stop (" + STOP + ") ?\n");

            var n = keyboard.nextLine().trim();
            if (n.equals(STOP)) {
                break;
            }
        }
    }

    public static double askForDistance(Scanner scanner) {
        while (true) { // loop until valid input
            System.out.print("\t What distance did you travel in km or miles ? Example: '12.5 km' or '12.5 mi'\n");
            try {
                var distance = scanner.nextDouble();
                var unit = scanner.nextLine().trim();
                return convertToKm(distance, unit);

            } catch (Exception e) {
                System.out.println("Invalid input. Please try again. Error: " + e.getMessage() + "\n");
            }
        }
    }

    public static double convertToKm(double distance, String unit) {
        if (unit.equals("km")) {
            return distance;
        } else if (unit.equals("mi")) {
            return distance * 1.60934;
        } else {
            throw new IllegalArgumentException("Invalid unit");
        }
    }
}