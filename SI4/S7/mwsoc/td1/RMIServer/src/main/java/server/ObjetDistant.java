package server;

import interfaces.Distante;
import interfaces.Resultat;
import interfaces.ResultatChild;

import java.rmi.RemoteException;

/**
 * A simple implementation of a printing interface.
 */
public class ObjetDistant implements Distante {
    @Override
    public void echo() throws RemoteException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Echo!");
    }

    @Override
    public Resultat getRes() throws RemoteException {
        return new ResultatChild("Info CB");
    }
}
