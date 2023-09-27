package client;

public class ClientIdImpl extends java.rmi.server.UnicastRemoteObject implements interfaces.ClientId {
    private final String name;

    public ClientIdImpl(String name) throws java.rmi.RemoteException {
        super();
        this.name = name;
    }

    @Override
    public String getName() throws java.rmi.RemoteException {
        return name;
    }

    @Override
    public synchronized void message(String message) throws java.rmi.RemoteException {
        System.out.println(message);
    }
}
