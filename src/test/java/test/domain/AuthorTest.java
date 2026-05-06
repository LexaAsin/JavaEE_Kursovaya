package test.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.Author;

public class AuthorTest {

    @Test
    public void testConstructorAndGetters() {
        Author author = new Author(1L, "Лев Толстой", "+7(999)123-45-67", "tolstoy@mail.ru", 4.9);
        
        assertEquals(1L, (long) author.getId());
        assertEquals("Лев Толстой", author.getFullName());
        assertEquals("+7(999)123-45-67", author.getPhone());
        assertEquals("tolstoy@mail.ru", author.getEmail());
        assertEquals(4.9, author.getRating(), 0.01);
    }

    @Test
    public void testSetters() {
        Author author = new Author();
        author.setId(2L);
        author.setFullName("Фёдор Достоевский");
        author.setPhone("+7(999)234-56-78");
        author.setEmail("dostoevsky@mail.ru");
        author.setRating(4.8);
        
        assertEquals(2L, (long) author.getId());
        assertEquals("Фёдор Достоевский", author.getFullName());
        assertEquals(4.8, author.getRating(), 0.01);
    }

    @Test
    public void testToString() {
        Author author = new Author(1L, "Лев Толстой", null, null, 4.9);
        assertTrue(author.toString().contains("Лев Толстой"));
    }
}