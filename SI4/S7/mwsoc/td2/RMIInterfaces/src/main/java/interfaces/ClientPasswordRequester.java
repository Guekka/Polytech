package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientPasswordRequester extends Remote, Serializable {
    String requestPassword() throws RemoteException;
}
