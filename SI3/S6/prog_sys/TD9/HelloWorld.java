/**
 * Created by lavirott on 13/05/2017, modified 27/04/2023.
 */
import java.util.Scanner;

public class HelloWorld {
    // Méthodes natives fournies grâce à une bibliothèque
    public static native void printCpp();
    public static native void printStringToCpp(String s);
    public static native String stringFromCpp();

    public static void test(String s) {
        System.out.println("Test appelé avec: " + s);
    }
    public native void callJavaMethod();

    public int entier;
    public native String toString();

    public static native int fib(int n);

    static {
        System.out.print("Loading Hello World native library...");
        System.loadLibrary("HelloWorld");
        System.out.println("done.");
    }

    public static void main(String args[]) {
        // Print from Java and from C/C++
        System.out.print("Hello ");
        HelloWorld.printCpp();

        // Print string to cpp
        HelloWorld.printStringToCpp("fromJava");
        String cpp = HelloWorld.stringFromCpp();
        System.out.println(cpp);

        HelloWorld hw = new HelloWorld();
        hw.callJavaMethod();

        System.out.println(hw.toString());

        System.out.println("Fib 3=" + fib(3));

        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez appuyer sur Entrée pour terminer le programme...\n");
        sc.nextLine();
        sc.close();
    }
}
