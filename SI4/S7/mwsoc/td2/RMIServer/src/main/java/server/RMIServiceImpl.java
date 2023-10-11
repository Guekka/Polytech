package server;

import data.ID;
import data.VoteValue;
import interfaces.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class RMIServiceImpl extends UnicastRemoteObject implements RMIService {
    VotingService votingService;
    AuthService authService;

    public RMIServiceImpl(VotingService votingService, AuthService authService) throws RemoteException {
        super();
        this.votingService = votingService;
        this.authService = authService;
    }

    @Override
    public List<Candidate> listCandidates() throws RemoteException {
        return votingService.getCandidates();
    }

    @Override
    public String getVoteMaterial(ID studentNumber, ClientPasswordRequester passwordRequester) throws RemoteException, AuthenticationFailure {
        return authService.authentificate(studentNumber, passwordRequester);
    }

    @Override
    public void vote(Map<ID, VoteValue> vote, ID studentNumber, String oneTimePassword) throws RemoteException, InvalidVoteCredentials {
        if (authService.validateOTP(studentNumber, oneTimePassword)) {
            votingService.vote(vote);
        }
    }

    @Override
    public Map<ID, Integer> requestResult() throws RemoteException {
        return votingService.requestResult();
    }
}
