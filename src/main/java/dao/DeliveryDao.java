package dao;

import domain.Book;
import domain.AppUser;
import domain.Delivery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDao {

    private final EmpConnBuilder builder = new EmpConnBuilder();

    public List<Delivery> findAll() {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT d.id, d.book_id, b.title as book_title, d.user_id, u.username, "
                + "d.date_input, d.date_output "
                + "FROM delivery d "
                + "JOIN books b ON d.book_id = b.id "
                + "JOIN app_user u ON d.user_id = u.id "
                + "ORDER BY d.id";
        try (Connection conn = builder.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("book_id"));
                book.setTitle(rs.getString("book_title"));

                AppUser user = new AppUser();
                user.setId(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));

                Delivery d = new Delivery();
                d.setId(rs.getLong("id"));
                d.setBook(book);
                d.setUser(user);
                d.setDateInput(rs.getDate("date_input").toLocalDate());
                if (rs.getDate("date_output") != null) {
                    d.setDateOutput(rs.getDate("date_output").toLocalDate());
                }
                deliveries.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    public Delivery findById(Long id) {
        String sql = "SELECT d.id, d.book_id, b.title as book_title, d.user_id, u.username, "
                + "d.date_input, d.date_output "
                + "FROM delivery d "
                + "JOIN books b ON d.book_id = b.id "
                + "JOIN app_user u ON d.user_id = u.id "
                + "WHERE d.id = ?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("book_id"));
                book.setTitle(rs.getString("book_title"));

                AppUser user = new AppUser();
                user.setId(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));

                Delivery d = new Delivery();
                d.setId(rs.getLong("id"));
                d.setBook(book);
                d.setUser(user);
                d.setDateInput(rs.getDate("date_input").toLocalDate());
                if (rs.getDate("date_output") != null) {
                    d.setDateOutput(rs.getDate("date_output").toLocalDate());
                }
                return d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Delivery delivery) {
        String sql = "INSERT INTO delivery (book_id, user_id, date_input, date_output) VALUES (?, ?, ?, ?)";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, delivery.getBook().getId());
            ps.setLong(2, delivery.getUser().getId());
            ps.setDate(3, java.sql.Date.valueOf(delivery.getDateInput()));
            ps.setDate(4, delivery.getDateOutput() != null ? java.sql.Date.valueOf(delivery.getDateOutput()) : null);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Delivery delivery) {
        String sql = "UPDATE delivery SET book_id = ?, user_id = ?, date_input = ?, date_output = ? WHERE id = ?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, delivery.getBook().getId());
            ps.setLong(2, delivery.getUser().getId());
            ps.setDate(3, java.sql.Date.valueOf(delivery.getDateInput()));
            ps.setDate(4, delivery.getDateOutput() != null ? java.sql.Date.valueOf(delivery.getDateOutput()) : null);
            ps.setLong(5, delivery.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM delivery WHERE id = ?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Delivery> findByUserId(Long userId) {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT d.id, d.book_id, b.title as book_title, d.user_id, u.username, "
                + "d.date_input, d.date_output "
                + "FROM delivery d "
                + "JOIN books b ON d.book_id = b.id "
                + "JOIN app_user u ON d.user_id = u.id "
                + "WHERE d.user_id = ? "
                + "ORDER BY d.id";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("book_id"));
                book.setTitle(rs.getString("book_title"));

                AppUser user = new AppUser();
                user.setId(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));

                Delivery d = new Delivery();
                d.setId(rs.getLong("id"));
                d.setBook(book);
                d.setUser(user);
                d.setDateInput(rs.getDate("date_input").toLocalDate());
                if (rs.getDate("date_output") != null) {
                    d.setDateOutput(rs.getDate("date_output").toLocalDate());
                }
                deliveries.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveries;
    }
    public void returnBook(Long deliveryId) {
        String sql = "UPDATE delivery SET date_output = CURRENT_DATE WHERE id = ?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, deliveryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}