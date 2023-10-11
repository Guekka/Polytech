package server;

import data.ID;
import interfaces.Candidate;
import interfaces.RMIService;
import interfaces.User;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try {
            RMIService implementation = getRmiService();

            // Export the object.
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(RMIService.RMI_NAME, implementation);
        } catch (RemoteException ex) {
            ex.printStackTrace();
            return;
        }
        System.out.println("Bound!");
        System.out.println("Server will wait forever for messages.");
    }

    private static RMIServiceImpl getRmiService() {
        var candidates = getCandidates();
        var users = new ArrayList<>(getUsers());

        users.addAll(candidates.stream().map(c -> (User) c).toList());

        try {
            var votingService = new VotingServiceImpl(candidates);
            var authService = new AuthServiceImpl(users);
            return new RMIServiceImpl(votingService, authService);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }

        // TODO: handle end
    }

    public static List<Candidate> getCandidates() {
        try {
            return List.of(
                    new CandidateImpl(new ID("123456"), "password", "John Doe", "I am John Doe"),
                    new CandidateImpl(new ID("654321"), "password", "Jane Doe", "I am Jane Doe")

            );
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }

        // TODO: do not hardcode candidates
    }

    public static List<User> getUsers() {
        try {
            return List.of(
                    new UserImpl(new ID("123456"), "password")

            );
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }


}
