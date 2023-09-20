package server;

import interfaces.Distante;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static void main(String[] args) {
        ObjetDistant implementation = new ObjetDistant();

        try {
            // Export the object.
            Registry registry = LocateRegistry.createRegistry(1099);
            Distante stub = (Distante) UnicastRemoteObject.exportObject(implementation, 1099);
            registry.rebind("Distante", stub);
        } catch (RemoteException ex) {
            ex.printStackTrace();
            return;
        }
        System.out.println("Bound!");
        System.out.println("Server will wait forever for messages.");
    }
}
