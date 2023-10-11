package interfaces;

public interface Config {
    Candidate[] getCandidates();

    void setCandidates(Candidate[] candidates);

    User[] getUsers();

    void setUsers(User[] users);

}
