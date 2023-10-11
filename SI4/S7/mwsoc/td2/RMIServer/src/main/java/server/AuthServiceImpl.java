package server;

import data.ID;
import interfaces.AuthenticationFailure;
import interfaces.ClientPasswordRequester;
import interfaces.InvalidVoteCredentials;
import interfaces.User;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple implementation of a printing interface.
 */
public class AuthServiceImpl implements AuthService {
    public static List<User> users;
    Map<ID, OTP> otps = new HashMap<>();

    protected AuthServiceImpl(List<User> users) {
        super();
        AuthServiceImpl.users = users;
    }

    @Override
    public String authentificate(ID studentNumber, ClientPasswordRequester passwordRequester) throws AuthenticationFailure {
        try {
            String password = passwordRequester.requestPassword();
            for (User user : users) {
                if (user.getStudentNumber().equals(studentNumber) && user.checkPassword(password)) {
                    return generateOTP(studentNumber);
                }
            }
        } catch (RemoteException e) {
            // TODO
        }
        throw new AuthenticationFailure();
    }

    private String generateOTP(ID studentNumber) {
        return otps.computeIfAbsent(studentNumber, k -> new OTP()).get();
    }

    public boolean validateOTP(ID studentNumber, String otp) throws InvalidVoteCredentials {
        if (this.otps.containsKey(studentNumber) && this.otps.get(studentNumber).isValid()) {
            this.otps.get(studentNumber).invalidate();
            return this.otps.get(studentNumber).get().equals(otp);
        }
        throw new InvalidVoteCredentials();
    }


}
