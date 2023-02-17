package test3.mailsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a mail server. The server is able to receive
 * mail items for storage, and deliver them to clients on demand.
 * 
 * @author David J. Barnes and Michael Kolling
 * @author M. Blay-Fornarino
 * @version 2022.11.2"
 */
class MailServer {
    // Storage for the arbitrary number of mail items to be stored
    // on the server.
    private final List<MailItem> items;

    /**
     * Construct a mail server.
     */
    MailServer() {
        items = new ArrayList<>();
    }

    /**
     * Return how many mail items are waiting for a user.
     * 
     * @param who
     *            The user to check for.
     * @return How many items are waiting.
     */
    int howManyMailItems(String who) {
        int count = 0;
        for (MailItem item : items) {
            if (item.getTo().equals(who)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Return how many mail items are waiting
     * @return How many items are waiting.
     */
    int howManyMailItems() {
        return items.size();
    }

    /**
     * Return the next mail item for a user or null
     * if there are none.
     * Side-effect: The user's next item is removed
     * from the list of mail items.
     * 
     * @param who
     *            The user requesting their next item.
     * @return The user's next item.
     */
    MailItem getNextMailItem(String who) {
        Iterator<MailItem> it = items.iterator();
        while (it.hasNext()) {
            MailItem item = it.next();
            if (item.getTo().equals(who)) {
                it.remove();
                return item;
            }
        }
        return null;
    }

    /**
     * Add the given mail item to the message list.
     * 
     * @param item
     *            The mail item to be stored on the server.
     * @return true if the item was stored; false otherwise.
     */
    boolean post(MailItem item) {
        return items.add(item);
    }
}
