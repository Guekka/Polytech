package interfaces;

import java.rmi.RemoteException;

public interface Distante extends java.rmi.Remote
{
    String SERVICE_NAME = "Distante";

    Service getService() throws RemoteException;
}
