package interfaces;

public interface ClientId extends java.rmi.Remote {
    String getName() throws java.rmi.RemoteException;

    void message(String message) throws java.rmi.RemoteException;
}
