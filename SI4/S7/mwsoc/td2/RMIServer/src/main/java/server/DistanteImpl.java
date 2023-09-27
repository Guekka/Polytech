package server;

import interfaces.Distante;

import java.rmi.RemoteException;

public class DistanteImpl implements Distante {

    public DistanteImpl() throws RemoteException {
    }

    @Override
    public ServiceImpl getService() throws RemoteException {
        return ServiceImpl.getInstance();
    }
}
