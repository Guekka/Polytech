package server;

import data.ID;
import interfaces.AuthenticationFailure;
import interfaces.ClientPasswordRequester;
import interfaces.InvalidVoteCredentials;

public interface AuthService {
    String authentificate(ID username, ClientPasswordRequester passwordRequester) throws AuthenticationFailure;

    boolean validateOTP(ID studentNumber, String otp) throws InvalidVoteCredentials;
}
