package test3.mailsystem;

public interface MailClientInterface {
    /**
     * Return the next mail item (if any) for this user presents on the linked server
     * else return null;
     */
    MailItem getNextMailItem();
    /**
     * Returns the next mail item (if any) for this user as a string
     */
    String nextMailItemToString();

    /**
     * Send the given message to the given recipient via the attached mail
     * server.
     *
     * @param to      The intended recipient.
     * @param message The text of the message to be sent.
     * @return true if the item was sent; false otherwise.
     */
    boolean sendMailItem(String to, String message);
    /**
     *
     * @return the name of the user of the mail client
     */
    String getUser();
}
