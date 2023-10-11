package server;

import data.ID;
import interfaces.Candidate;

import java.io.Serializable;
import java.rmi.RemoteException;

public class CandidateImpl extends UserImpl implements Candidate, Serializable {
    String name;
    String pitch;

    public CandidateImpl(ID id, String password, String name, String pitch) throws RemoteException {
        super(id, password);

        this.name = name;
        this.pitch = pitch;
    }

    @Override
    public int hashCode() {
        return studentNumber.hashCode();
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public String getPitch() throws RemoteException {
        return pitch;
    }
}
