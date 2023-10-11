package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Service extends Remote {
    int getVal() throws RemoteException;

    void setVal(int val, ClientId id) throws RemoteException;
}
