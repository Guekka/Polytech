package server;

import interfaces.Distante;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static void main(String[] args) throws RemoteException {
        DistanteImpl implementation = new DistanteImpl();

        try {
            // Export the object.
            Registry registry = LocateRegistry.createRegistry(1099);
            var stub = (Distante) UnicastRemoteObject.exportObject(implementation, 0);
            registry.rebind(Distante.SERVICE_NAME, stub);
        } catch (RemoteException ex) {
            ex.printStackTrace();
            return;
        }
        System.out.println("Bound!");
        System.out.println("Server will wait forever for messages.");
    }
}
