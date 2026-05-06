package test.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.Author;
import domain.Book;

public class BookTest {

    @Test
    public void testConstructorAndGetters() {
        Author author = new Author(1L, "Лев Толстой", null, null, 4.9);
        Book book = new Book(1L, "Война и мир", "Твёрдый", "Эксмо", 1869, "Роман", 1L, author);
        
        assertEquals(1L, (long) book.getId());
        assertEquals("Война и мир", book.getTitle());
        assertEquals("Твёрдый", book.getBinding());
        assertEquals("Эксмо", book.getPublisher());
        assertEquals(1869, (int) book.getYear());
        assertEquals("Роман", book.getGenre());
        assertEquals(author, book.getAuthor());
        assertEquals("Лев Толстой", book.getAuthorName());
    }
}