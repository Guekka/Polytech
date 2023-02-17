import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDictionnaryTest {
    /*
    ------------------------------------ ADD
    */

    @Test
    void testAddDuplicateItem() {
        ItemDictionnary<Book> dictionary = new ItemDictionnary<>();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist (2)", "Paulo Coelho", 1988, "1234567890");
        dictionary.addItem(book1);
        dictionary.addItem(book2);
        assertEquals(1, dictionary.size());
        assertEquals(2, dictionary.get("1234567890").size());
    }

    @Test
    void testAddItemWithDifferentIds() {
        ItemDictionnary<Book> dictionary = new ItemDictionnary<>();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("Veronika Decides to Die", "Paulo Coelho", 1998, "0987654321");
        dictionary.addItem(book1);
        dictionary.addItem(book2);
        assertEquals(2, dictionary.size());
        assertEquals(1, dictionary.get("1234567890").size());
        assertEquals(1, dictionary.get("0987654321").size());
    }


    /*
     ------------------------------------ FIND
     */
    @Test
    void testFindItemsWithExistingId() {
        ItemDictionnary<Book> dictionary = new ItemDictionnary<>();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        dictionary.addItem(book1);
        dictionary.addItem(book2);
        List<Book> items = dictionary.findItems(book1);
        assertEquals(2, items.size());
        assertTrue(items.contains(book1));
        assertTrue(items.contains(book2));
    }

    @Test
    void testFindItemsWithNonExistingId() {
        ItemDictionnary<Book> dictionary = new ItemDictionnary<>();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("Veronika Decides to Die", "Paulo Coelho", 1998, "0987654321");
        dictionary.addItem(book1);
        List<Book> items = dictionary.findItems(book2);
        assertNull(items);
    }

    @Test
    void testFindItemsWithNullItem() {
        ItemDictionnary<Book> dictionary = new ItemDictionnary<>();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        dictionary.addItem(book1);
        List<Book> items = dictionary.findItems(null);
        assertNull(items);
    }

    /*
     ------------------------------------ FIND Matchable
      */
    @Test
    void testFindMatchableItems() {
        ItemDictionnary<Book> dictionary = new ItemDictionnary<>();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("Veronika Decides to Die", "Paulo Coelho", 1998, "0987654321");
        Book book3 = new Book("The Alchemist", "Paulo Coelho", 1988, "0000000000");
        dictionary.addItem(book1);
        dictionary.addItem(book2);
        dictionary.addItem(book3);
        List<Book> searchResults = dictionary.findMatchableItems("Alchemist");
        assertEquals(2, searchResults.size());
        assertTrue(searchResults.contains(book1));
        assertTrue(searchResults.contains(book3));
    }

    @Test
    void testFindMultipleMatchableItems() {
        ItemDictionnary<Book> dictionary = new ItemDictionnary<>();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book3 = new Book("Veronika Decides to Die", "The Paulo Coelho", 1998, "0987654321");
        dictionary.addItem(book1);
        dictionary.addItem(book2);
        dictionary.addItem(book3);
        List<Book> searchResults = dictionary.findMatchableItems("The");
        assertEquals(3, searchResults.size());
    }

/*
 ------------------------------------ REMOVE
*/

    @Test
    void testRemoveItem() {
        ItemDictionnary<Book> dictionary = new ItemDictionnary<>();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("Veronika Decides to Die", "Paulo Coelho", 1998, "0987654321");
        Book book3 = new Book("The Alchemist", "Paulo Coelho", 1988, "0000000000");
        dictionary.addItem(book1);
        dictionary.addItem(book2);
        dictionary.addItem(book3);
        assertTrue(dictionary.removeItem(book1));
        assertFalse(dictionary.removeItem(book1));
        assertEquals(2, dictionary.size());
        assertNull(dictionary.get("1234567890"));
    }

    @Test
    void testRemoveItemWithMultipleCopies() {
        ItemDictionnary<Book> dictionary = new ItemDictionnary<>();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        dictionary.addItem(book1);
        dictionary.addItem(book2);
        assertTrue(dictionary.removeItem(book1));
        assertEquals(1, dictionary.size());
        assertEquals(1, dictionary.get("1234567890").size());
    }
}