package binarypk;

public class MalformedStringException extends Exception {
    public MalformedStringException(String child) {
        super(child);
    }
}
