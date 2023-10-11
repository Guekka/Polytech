package server;

import data.ID;
import data.VoteValue;
import interfaces.Candidate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VotingServiceImpl implements VotingService {
    Map<ID, Integer> voteValues = new ConcurrentHashMap<>();

    VotingServiceImpl(List<Candidate> candidates) {
        try {
            for (Candidate candidate : candidates) {
                voteValues.put(candidate.getStudentNumber(), 0);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Candidate> getCandidates() {
        return AuthServiceImpl.users.stream()
                .filter(Candidate.class::isInstance)
                .map(c -> (Candidate) c)
                .toList();
    }

    @Override
    public void vote(Map<ID, VoteValue> vote) {
        for (var entry : vote.entrySet()) {
            voteValues.compute(entry.getKey(), (candidate, integer) -> integer + entry.getValue().value());
        }
    }

    @Override
    public Map<ID, Integer> requestResult() {
        return Map.copyOf(voteValues);
    }
}
