package interfaces;


import java.io.Serializable;
import java.rmi.RemoteException;

public interface Candidate extends User, Serializable {
    String getName() throws RemoteException;

    String getPitch() throws RemoteException; // TODO: add video maybe
    
}
