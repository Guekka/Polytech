package test3.mailsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtendedMailClientTest {

    String JOHN = "John";
    String PAUL = "Paul";
    String MSG = "Hello, my friends";

    String SUBJECT = "To say Hello";
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
    void sendMailExtendedItemTest() {
        johnMailClient.sendMailItem(PAUL, SUBJECT, MSG);
        MailItem item = server.getNextMailItem(PAUL);
        testValidItem(item);
        assertTrue(item.toString().contains(SUBJECT));

        item = server.getNextMailItem(PAUL);
        assertTrue(item == null);

        johnMailClient.sendMailItem(PAUL, MSG);
        item = server.getNextMailItem(PAUL);
        testValidItem(item);
        //LMS forbids use of asserFalse !!
        assertTrue(!(item.toString().contains("subject")));
    }


    private void testValidItem(MailItem item) {
        assertTrue(item != null );
        assertEquals(MSG, item.getMessage());
        assertEquals(JOHN, item.getFrom());
        assertEquals(PAUL, item.getTo());
    }


}