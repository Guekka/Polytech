package test3.mailsystem;
/**
 * A class to model a simple email client. The client is run by a particular
 * user, and sends and retrieves mail via a particular server.
 *
 * @author David J. Barnes and Michael Kolling
 * @author MBF
 */

public class MailClient implements MailClientInterface {
    //The server used for sending and receiving.
    MailServer server;
    //The user  running this client.
    String user;
    /**
     * Create a mail client run by user and dependent on the the given server.
     */
    MailClient(MailServer server, String user) {
        this.server = server;
        this.user = user;
    }

    /**
     * Return the next mail item (if any) for this user presents on the linked server
     * else return null;
     */
    @Override
    public MailItem getNextMailItem() {
         return server.getNextMailItem(user);
    }

    /**
     * Returns the next mail item (if any) for this user as a string
     */
    @Override
    public String nextMailItemToString() {
        var item = getNextMailItem();
        if (item == null)
            return "No new mail.";

        return item.toString();
    }

    /**
     * Send the given message to the given recipient via the attached mail
     * server.
     *
     * @param to      The intended recipient.
     * @param message The text of the message to be sent.
     * @return true if the item was sent; false otherwise.
     */
    @Override
    public boolean sendMailItem(String to, String message) {
        MailItem item = new MailItem(user, to, message);
        return server.post(item);
    }

    /**
     * Send the given message to the given recipient via the attached mail
     * server.
     *
     * @param to      The intended recipient.
     * @param subject      The subject
     * @param message The text of the message to be sent.
     * @return true if the item was sent; false otherwise.
     */
    boolean sendMailItem(String to, String subject, String message) {
        MailItem item = new MailItemWithSubject(user, to, subject, message);
        return server.post(item);
    }

    /**
     * Send the given message to all the members of the group via the attached mail
     * server.
     *
     * @param to      The group of recipients.
     * @param message The text of the message to be sent.
     * @return true if the item was sent to all the recipient; false otherwise.
     */

    public boolean sendMailItem(Group to, String message) {
        boolean result = true;
        for (String recipient : to.getMembers()) {
            result = result && sendMailItem(recipient, message);
        }
        return result;
    }

    //It is better to work on the tests than on the hand!  These codes are only there to help understand.
    public static void main(String[] args) {
        // ---------------
        String JOHN = "John";
        String PAUL = "Paul";
        String MSG = "Yo! Ni hao! Tervist!";
        MailServer server = new MailServer();
        MailClient johnMailClient = new MailClient(server, JOHN);
        MailClient paulMailClient = new MailClient(server, PAUL);
        johnMailClient.sendMailItem(PAUL, MSG);
        johnMailClient.sendMailItem(PAUL, MSG);
        System.out.println(paulMailClient.nextMailItemToString());
        System.out.println(paulMailClient.nextMailItemToString());
        System.out.println(paulMailClient.nextMailItemToString());
    }


    /**
     *
     * @return the name of the user of the mail client
     */
    @Override
    public String getUser() {
        return user;
    }

}