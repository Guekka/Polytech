package test3.mailsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
NE PAS MODIFIER -- LE TEST DOIT PASSER SANS MODIFICATION --
A VOUS D ADAPTER VOTRE IMPLEMENTATION
 */
class MailClientTestToGiveTest {

    String JOHN = "John";
    String PAUL = "Paul";
    String MSG = "Salut!";

    String SUBJECT = "subject";

    MailServer server;
    MailClient johnMailClient;
    MailClient paulMailClient;

    @BeforeEach
    void setUp() {
        server = new MailServer();
        johnMailClient = new MailClient(server, JOHN);
        paulMailClient = new MailClient(server, PAUL);
    }

    @Test
    void initialisationTest() {
        //LMS interdit notEquals, false etc...
        assertTrue(server != null);
        assertTrue(johnMailClient != null);
        assertTrue(paulMailClient != null);
        assertEquals(JOHN, johnMailClient.getUser());
        assertEquals(PAUL, paulMailClient.getUser());
    }

    @Test
    void sendMailItemTest() {
        johnMailClient.sendMailItem(PAUL, MSG);
        MailItem item = server.getNextMailItem(PAUL);
        testValidItem(item);
        item = server.getNextMailItem(PAUL);
        assertTrue(item == null);
    }

    @Test
    void sendMailItemWithSubjectTest() {
        johnMailClient.sendMailItem(PAUL, SUBJECT, MSG);
        MailItem item = server.getNextMailItem(PAUL);
        testValidItem(item);
        item = server.getNextMailItem(PAUL);
        assertTrue(item == null);
    }

    @Test
    void getNextMailItemTest() {
        server.post(new MailItem(JOHN, PAUL, MSG));
        //paulMailClient test
        MailItem item = paulMailClient.getNextMailItem();
        testValidItem(item);
        item = paulMailClient.getNextMailItem();
        assertTrue(item == null);
    }




    @Test
    void doubleMessagesSentTest() {
        johnMailClient.sendMailItem(PAUL, "1_"+ MSG);
        paulMailClient.sendMailItem(JOHN, "2_" + MSG);
        johnMailClient.sendMailItem(PAUL, "3_"+MSG);
        paulMailClient.sendMailItem(JOHN, "4_" + MSG);

        //John read first
        String itemToJohn = johnMailClient.nextMailItemToString();
        assertEquals("from='Paul', to='John', message='2_Salut!'", itemToJohn);
        //Paul read
        String itemToPaul = paulMailClient.nextMailItemToString();
        assertEquals("from='John', to='Paul', message='1_Salut!'", itemToPaul);
        //Paul read again
        itemToPaul = paulMailClient.nextMailItemToString();
        assertEquals("from='John', to='Paul', message='3_Salut!'", itemToPaul);
        //John read last
        itemToJohn = johnMailClient.nextMailItemToString();
        assertEquals("from='Paul', to='John', message='4_Salut!'", itemToJohn);
    }






    /**
     * LMS FORBID usage of assertFalse
     *
     * @param equals
     */
    private void assertlocalfalse(boolean equals, String reason) {
        assertTrue(equals == false, reason);
    }




    private void testValidItem(MailItem item) {
        assertTrue(item != null);
        assertEquals(MSG, item.getMessage());
        assertEquals(JOHN, item.getFrom());
        assertEquals(PAUL, item.getTo());

        if (item instanceof MailItemWithSubject) {
            assertEquals(SUBJECT, ((MailItemWithSubject) item).getSubject());
        }


    }


}