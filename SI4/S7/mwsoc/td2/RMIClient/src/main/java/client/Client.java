package client;

import data.ID;
import data.VoteValue;
import interfaces.Candidate;
import interfaces.RMIService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;

public class Client {

    public static void main(String[] args) {
        try {
            var registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            var service = (RMIService) registry.lookup(RMIService.RMI_NAME);

            List<Candidate> candidates = service.listCandidates();
            System.out.println("Candidates:");
            for (var candidate : candidates) {
                System.out.println(candidate);
            }

            // readline
            System.out.println("Enter your student number: ");
            var studentNumber = new ID(new Scanner(System.in).nextLine());

            var passwordRequester = new ClientPasswordRequesterImpl();
            var otp = service.getVoteMaterial(studentNumber, passwordRequester);

            System.out.println("One-time password: " + otp);

            Map<ID, VoteValue> votes = getVotes(candidates);
            service.vote(votes, studentNumber, otp);

            System.out.println("Result: " + service.requestResult());

        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
    }

    private static Map<ID, VoteValue> getVotes(List<Candidate> candidates) throws RemoteException {
        var res = new HashMap<ID, VoteValue>();
        for (var candidate : candidates) {
            var vote = getVote(candidate);
            res.put(candidate.getStudentNumber(), vote);
        }
        return res;
    }

    private static VoteValue getVote(Candidate candidate) {
        Arrays.stream(VoteValue.values()).map(
                vote -> vote.value() + ": " + vote
        ).forEach(System.out::println);
        int vote = 0;
        try {
            System.out.println("Enter your vote for " + candidate.getName() + ": ");
            vote = Integer.parseInt(new Scanner(System.in).nextLine());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return VoteValue.fromValue(vote);
    }
}
