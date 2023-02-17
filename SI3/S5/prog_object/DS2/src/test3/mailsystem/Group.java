package test3.mailsystem;

import java.util.List;

public interface Group {
    String[] getMembers();
    String getName();
    void addMember(String member);
}
