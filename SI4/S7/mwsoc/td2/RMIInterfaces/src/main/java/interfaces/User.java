package interfaces;

import data.ID;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface User extends Remote, Serializable {
    ID getStudentNumber() throws RemoteException;

    boolean checkPassword(String password) throws RemoteException;
}
