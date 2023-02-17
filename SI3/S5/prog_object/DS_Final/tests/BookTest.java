import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    public void testConstructorWithFour() {
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "978-3-16-148410-0");
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
        assertEquals(1925, book.getYear());
        assertEquals("978-3-16-148410-0", book.getId());
    }

    @Test
    public void testConstructorWithNullISBN() {
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, null);
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
        assertEquals(1925, book.getYear());
        assertEquals("unknown", book.getId());
    }

    @Test
    void testConstructorWithNullTitle() {
        Book book = new Book(null, "F. Scott Fitzgerald", 1925);
        assertEquals("undefined", book.getTitle());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
        assertEquals(1925, book.getYear());
    }

    @Test
    void testConstructorWithNullAuthor() {
        Book book = new Book("The Great Gatsby", null, 1925);
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("unknown", book.getAuthor());
        assertEquals(1925, book.getYear());
    }

    @Test
    void testConstructorWithNullTitleAndAuthor() {
        Book book = new Book(null, null, 1925);
        assertEquals("undefined", book.getTitle());
        assertEquals("unknown", book.getAuthor());
        assertEquals(1925, book.getYear());
    }

    @Test
    void testEqualsWithSameISBN() {
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testEqualsWithDifferentISBNs() {
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist", "Paulo Coelho", 1988, "0987654321");
        assertNotEquals(book1, book2);
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testEqualsWithUnknownISBNs() {
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "unknown");
        Book book2 = new Book("The Alchemist", "Paulo Coelho", 1988, "unknown");
        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testEqualsWithUnknownAndNonUnknownISBNs() {
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist", "Paulo Coelho", 1988, "unknown");
        assertNotEquals(book1, book2);
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testEqualsWithDifferentTitles() {
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "unknown");
        Book book2 = new Book("Veronika Decides to Die", "Paulo Coelho", 1998, "unknown");
        assertNotEquals(book1, book2);
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testEqualsWithDifferentAuthors() {
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "unknown");
        Book book2 = new Book("The Alchemist", "J.K. Rowling", 1998, "unknown");
        assertNotEquals(book1, book2);
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testEqualsWithDifferentObjectTypes() {
        Book book = new Book("The Alchemist", "Paulo Coelho", 1988, "unknown");
        String str = "not a book";
        assertNotEquals(book, str);
    }

    @Test
    void testMatch() {
        Matchable book = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        assertTrue(book.match("Alchemist"));
        assertTrue(book.match("Coelho"));
        assertTrue(book.match("88"));
        assertTrue(book.match("890"));
        assertFalse(book.match("1984"));
        assertFalse(book.match("Jane Austen"));
    }

    @Test
    void testSetTitle() {
        Book book = new Book("The Alchemist", "Paulo Coelho", 1988, "123-456-789");
        book.setTitle("The Alchemist 2");
        assertEquals("The Alchemist 2", book.getTitle());
    }

    @Test
    void testCopy() {
        Book book = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book clone = book.copy();
        assertEquals(book, clone);
        //pour moodle
        assertNotSame(book, clone);
    }

    @Test
    void testMerge() {
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("Veronika Decides to Die", "Paulo Coelho", 1988, "1234567890");
        Optional<Book> mergedBook = book1.merge(book2);
        assertTrue(mergedBook.isPresent());
        assertEquals("The Alchemist or Veronika Decides to Die", mergedBook.get().getTitle());
        assertEquals("Paulo Coelho", mergedBook.get().getAuthor());
        assertEquals(1988, mergedBook.get().getYear());
        assertEquals("1234567890", mergedBook.get().getId());
    }


    @Test
    void testMergeWithDifferentYears() {
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist", "Paulo Coelho", 1998, "0987654321");
        Optional<Book> mergedBook = book1.merge(book2);
        assertFalse(mergedBook.isPresent());
    }

    @Test
    void testMergeWithUnknownId() {
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "unknown");
        Book book2 = new Book("The Alchemist", "Paulo Coelho", 1988, "0987654321");
        Optional<Book> mergedBook = book1.merge(book2);
        assertTrue(mergedBook.isPresent());
        assertEquals("The Alchemist", mergedBook.get().getTitle());
        assertEquals("Paulo Coelho", mergedBook.get().getAuthor());
        assertEquals(1988, mergedBook.get().getYear());
        assertEquals("0987654321", mergedBook.get().getId());
    }

    @Test
    void testMergeWithUnknownAuthor() {
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("undefined", "unknown", 1988, "1234567890");
        Optional<Book> mergedBook = book1.merge(book2);
        assertTrue(mergedBook.isPresent());
        assertEquals("The Alchemist", mergedBook.get().getTitle());
        assertEquals("Paulo Coelho", mergedBook.get().getAuthor());
        assertEquals(1988, mergedBook.get().getYear());
        assertEquals("1234567890", mergedBook.get().getId());
    }

    @Test
    void testRemoveItemWithNullItem() {
        ItemDictionnary<Book> dictionary = new ItemDictionnary<>();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        dictionary.addItem(book1);
        assertFalse(dictionary.removeItem(null));
        assertEquals(1, dictionary.size());
    }
}