package test3.mailsystem;

import java.util.ArrayList;
import java.util.List;

public class GroupImpl implements Group {
    private List<String> members = new ArrayList<>();

    private String name;

    public GroupImpl(String name) {
        this.name = name;
    }

    @Override
    public String[] getMembers() {
        return members.toArray(new String[0]);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addMember(String member) {
        members.add(member);
    }
}
