package test3.mailsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MailClientWithGroupsTest {

    String JOHN = "John";

    List<String> persons = new ArrayList<>(Arrays.asList("Emma","Pierre","Yassine","Paul",JOHN));

    Map<String, MailClient> mailClients = new HashMap<>();

    //Since there are no modifiers for the group name this constructor is essential.
    Group group;

    //Better but not for the test !
    // Group group = new GroupImpl("Friends",
    //        new ArrayList<>(Arrays.asList("Emma","Pierre","Yassine")));
    String MSG = "Hello, my friends";
    MailServer server;
    @BeforeEach
    void setUp() {
        group = new GroupImpl("Friends");
        group.addMember("Emma");
        group.addMember("Pierre");
        group.addMember("Yassine");

        server = new MailServer();
        for (String person : persons){
            mailClients.put(person, new MailClient(server,person));
        }
    }
    //this test is not in its place,
    // it should be in a test file of the GroupImpl class.
    // I put it here to limit the number of files during the "control".
    @Test
    void groupMembersTest() {
       String [] expected = new String[]{"Emma","Pierre","Yassine"};
       Arrays.sort(expected);
       String[] members = group.getMembers();
       Arrays.sort(members);
       assertEquals(3, members.length);
       //Always a pbme with LMS for complex tests.
       assertTrue(Arrays.equals(expected,members));
    }

    //this test is not in its place,
    // it should be in a test file of the GroupImpl class.
    // I put it here to limit the number of files during the "control".
    @Test
    void groupNameTest() {
        assertEquals("Friends", group.getName());
    }

    @Test
    void sendMailExtendedItemToGroupTest() {
        mailClients.get(JOHN).sendMailItem(group, MSG);
        for (String member : group.getMembers()){
            MailItem item = mailClients.get(member).getNextMailItem();
            assertEquals(JOHN,item.getFrom());
            assertEquals(member,item.getTo());
            assertEquals(MSG,item.getMessage());
        }
    }

    @Test
    void sendMailExtendedItemToEmptyGroupTest() {
        mailClients.get(JOHN).sendMailItem(new GroupImpl("empty"), MSG);
        assertEquals(0,server.howManyMailItems(),"For an empty group, no mail are sent");
    }



}