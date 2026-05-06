package dao;

import domain.Author;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {

    private final EmpConnBuilder builder = new EmpConnBuilder();

    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT id, full_name, phone, email, rating FROM authors ORDER BY id";
        try (Connection conn = builder.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getLong("id"));
                author.setFullName(rs.getString("full_name"));
                author.setPhone(rs.getString("phone"));
                author.setEmail(rs.getString("email"));
                author.setRating(rs.getDouble("rating"));
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Author findById(Long id) {
        String sql = "SELECT id, full_name, phone, email, rating FROM authors WHERE id = ?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Author author = new Author();
                author.setId(rs.getLong("id"));
                author.setFullName(rs.getString("full_name"));
                author.setPhone(rs.getString("phone"));
                author.setEmail(rs.getString("email"));
                author.setRating(rs.getDouble("rating"));
                return author;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Author author) {
        String sql = "INSERT INTO authors (full_name, phone, email, rating) VALUES (?, ?, ?, ?)";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getFullName());
            ps.setString(2, author.getPhone());
            ps.setString(3, author.getEmail());
            ps.setDouble(4, author.getRating());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Author author) {
        String sql = "UPDATE authors SET full_name=?, phone=?, email=?, rating=? WHERE id=?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getFullName());
            ps.setString(2, author.getPhone());
            ps.setString(3, author.getEmail());
            ps.setDouble(4, author.getRating());
            ps.setLong(5, author.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM authors WHERE id = ?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}