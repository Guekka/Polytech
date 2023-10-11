package client;

import java.rmi.RemoteException;

public class ClientPasswordRequesterImpl extends java.rmi.server.UnicastRemoteObject implements interfaces.ClientPasswordRequester {
    protected ClientPasswordRequesterImpl() throws RemoteException {
    }

    @Override
    public String requestPassword() throws RemoteException {
        System.out.println("Password required to access the server. Please enter it below.");
        System.out.print("Password: ");
        return new java.util.Scanner(System.in).nextLine();
    }
}
