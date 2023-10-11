package server;

import data.ID;
import interfaces.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserImpl extends UnicastRemoteObject implements User {
    ID studentNumber;
    String password;

    public UserImpl(ID id, String password) throws RemoteException {
        this.studentNumber = id;
        this.password = password;
    }

    @Override
    public ID getStudentNumber() throws RemoteException {
        return studentNumber;
    }

    @Override
    public boolean checkPassword(String password) throws RemoteException {
        return this.password.equals(password);
    }
}
