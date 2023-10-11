package server;

public class OTP {
    private final String otp;
    boolean valid = true;

    public OTP() {
        this.otp = String.valueOf((int) (Math.random() * 1000000));
    }

    public String get() {
        return otp;
    }

    void invalidate() {
        valid = false;
    }

    public boolean isValid() {
        return valid;
    }
}
