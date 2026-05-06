package dao;

import domain.AppUser;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppUserDao {

    private final EmpConnBuilder builder = new EmpConnBuilder();

    public List<AppUser> findAll() {
        List<AppUser> users = new ArrayList<>();
        String sql = "SELECT id, username, password, first_name, last_name, phone, role FROM app_user ORDER BY id";
        try (Connection conn = builder.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                AppUser user = new AppUser();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public AppUser findById(Long id) {
        String sql = "SELECT id, username, password, first_name, last_name, phone, role FROM app_user WHERE id = ?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AppUser user = new AppUser();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(String username, String password, String firstName, String lastName, String phone, String role) {
        String sql = "INSERT INTO app_user (username, password, first_name, last_name, phone, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, phone);
            ps.setString(6, role);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Long id, String username, String password, String firstName, String lastName, String phone, String role) {
        String sql = "UPDATE app_user SET username=?, password=?, first_name=?, last_name=?, phone=?, role=? WHERE id=?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, phone);
            ps.setString(6, role);
            ps.setLong(7, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM app_user WHERE id = ?";
        try (Connection conn = builder.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}