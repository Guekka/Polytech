package server;

import interfaces.ClientId;
import interfaces.Service;

import java.rmi.RemoteException;

/**
 * A simple implementation of a printing interface.
 */
public class ServiceImpl extends java.rmi.server.UnicastRemoteObject implements Service {
    // singleton
    private static ServiceImpl instance = null;
    private int val;

    private ServiceImpl() throws RemoteException {
        this.val = 1;
    }

    public static ServiceImpl getInstance() throws RemoteException {
        if (instance == null) {
            instance = new ServiceImpl();
        }
        return instance;
    }

    @Override
    public int getVal() throws RemoteException {
        System.out.println(Thread.currentThread().getName() + " val renvoyée : " + val + "au Client ");
        return this.val;
    }

    @Override
    public synchronized void setVal(int v, ClientId id) throws RemoteException {
        id.message(Thread.currentThread().getName() + " val reçue : " + v + " du Client ");
        val *= v;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
