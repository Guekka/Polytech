import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Test
    void testAddBooks() {
        Library library = new Library();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist 2", "Paulo Coelho*", 1988, "1234567890");
        Book book4 = new Book("The Alchemist 2-a", "Paulo Coelho", 1988, "1234567890");
        Book book3 = new Book("The Alchemist 3", "Paulo Coelho", 1988, "1111111111");
        library.addItem(book1);
        library.addItem(book2);
        library.addItem(book3);
        library.addItem(book4);
        assertEquals(3, library.get("1234567890").size());
        assertEquals(1, library.get("1111111111").size());
    }

    @Test
    void testMergeBooksWithDifferentNonMergeableBooks() {
        Library library = new Library();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist 2", "Paulo Coelho", 1998, "0987654321");
        library.addItem(book2);
        Optional<Book> mergedBook = library.mergeBooks(book1);
        assertTrue(mergedBook.isPresent());
        assertEquals("The Alchemist", mergedBook.get().getTitle());
        assertEquals("Paulo Coelho", mergedBook.get().getAuthor());
        assertEquals(1988, mergedBook.get().getYear());
        assertEquals("1234567890", mergedBook.get().getId());
    }

    @Test
    void testMergeBooksWithMultipleOtherBooks() {
        Library library = new Library();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist 2", "Paulo Coelho*", 1988, "1234567890");
        Book book4 = new Book("The Alchemist 2-a", "Paulo Coelho", 1988, "1234567890");
        Book book3 = new Book("The Alchemist 3", "Paulo Coelho", 1988, "1111111111");
        library.addItem(book2);
        library.addItem(book3);
        library.addItem(book4);
        Optional<Book> mergedBook = library.mergeBooks(book1);
        assertTrue(mergedBook.isPresent());
        assertEquals("The Alchemist 2-a", mergedBook.get().getTitle());
        assertEquals("Paulo Coelho*", mergedBook.get().getAuthor());
        assertEquals(1988, mergedBook.get().getYear());
        assertEquals("1234567890", mergedBook.get().getId());
    }

    @Test
    void testMergeBooksWithNullBook() {
        Library library = new Library();
        Optional<Book> mergedBook = library.mergeBooks(null);
        assertFalse(mergedBook.isPresent());
    }


    @Test
    void testSortByTitle() {
        Library library = new Library();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist 2", "Paulo Coelho", 1998, "1234567890");
        Book book3 = new Book("The Alchemist 3", "Paulo Coelho", 2008, "9876543210");
        Book book4 = new Book("The Alchemist 4", "J.K. Rowling", 2018, "1231231230");
        library.addItem(book3);
        library.addItem(book2);
        library.addItem(book4);
        library.addItem(book1);
        List<Book> sortedBooks = library.sortByTitle();
        assertEquals(book1, sortedBooks.get(0));
        assertEquals(book2, sortedBooks.get(1));
        assertEquals(book3, sortedBooks.get(2));
        assertEquals(book4, sortedBooks.get(3));
    }


    @Test
    void testSortByAuthor() {
        Library library = new Library();
        Book book1 = new Book("The Alchemist", "Paulo Coelho", 1988, "1234567890");
        Book book2 = new Book("The Alchemist 2", "Paulo Coelho", 1988, "1234567890");
        Book book3 = new Book("The Alchemist 3", "Paulo Coelho de Souza", 1988, "1234567890");
        Book book4 = new Book("The Alchemist 4", "J.K. Rowling", 2018, "1231231230");
        library.addItem(book3);
        library.addItem(book2);
        library.addItem(book4);
        library.addItem(book1);
        List<Book> sortedBooks = library.sortByAuthor();
        assertEquals(book4, sortedBooks.get(0));
        assertEquals(book3, sortedBooks.get(3));
    }

    @Test
    void testListAddedBooks_noAddedBooks() {
        Library library = new Library();
        List<Book> addedBooks = library.listAddedBooks();
        assertTrue(addedBooks.isEmpty());
    }

    @Test
    void testListAddedBooks_oneAddedBook_stillPresent() {
        Library library = new Library();
        Book book = new Book("Title", "Author", 2000, "ISBN");
        library.addItem(book);
        List<Book> addedBooks = library.listAddedBooks();
        assertEquals(1, addedBooks.size());
        assertEquals(book, addedBooks.get(0));
    }

    @Test
    void testListAddedBooks_oneAddedBook_removed() {
        Library library = new Library();
        Book book = new Book("Title", "Author", 2000, "ISBN");
        library.addItem(book);
        library.removeItem(book);
        List<Book> addedBooks = library.listAddedBooks();
        assertEquals(0, addedBooks.size());
    }
}