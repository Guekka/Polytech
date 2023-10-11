package interfaces;

import data.ID;
import data.VoteValue;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RMIService extends Remote, Serializable {
    String RMI_NAME = "Service";

    List<Candidate> listCandidates() throws RemoteException;

    /// @return One-time password
    String getVoteMaterial(ID studentNumber, ClientPasswordRequester passwordRequester) throws RemoteException, AuthenticationFailure;

    void vote(Map<ID, VoteValue> vote, ID studentNumber, String oneTimePassword) throws RemoteException, InvalidVoteCredentials;

    Map<ID, Integer> requestResult() throws RemoteException;
}
