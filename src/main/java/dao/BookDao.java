package dao;

import domain.Author;
import domain.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private final EmpConnBuilder builder = new EmpConnBuilder();
    private final AuthorDao authorDao = new AuthorDao();

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, author_id, title FROM books ORDER BY id";
        try (Connection conn = builder.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                Long authorId = rs.getLong("author_id");
                if (authorId != 0) {
                    Author author = authorDao.findById(authorId);
                    book.setAuthor(author);
                }
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book findById(Long id) {
        String sql = "SELECT id, author_id, title FROM books WHERE id = ?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                Long authorId = rs.getLong("author_id");
                if (authorId != 0) {
                    Author author = authorDao.findById(authorId);
                    book.setAuthor(author);
                }
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Book book) {
        String sql = "INSERT INTO books (author_id, title) VALUES (?, ?)";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, book.getAuthor().getId());
            ps.setString(2, book.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Book book) {
        String sql = "UPDATE books SET author_id=?, title=? WHERE id=?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, book.getAuthor().getId());
            ps.setString(2, book.getTitle());
            ps.setLong(3, book.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}