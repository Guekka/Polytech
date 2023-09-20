package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Distante extends Remote {
    void echo() throws RemoteException;

    Resultat getRes() throws RemoteException;
}
