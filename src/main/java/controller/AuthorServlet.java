package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ConnectionProperty;
import dao.EmpConnBuilder;
import domain.Author;

@WebServlet("/authors")
public class AuthorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ConnectionProperty prop;
    String selectAllAuthors = "SELECT id, full_name, phone, email, rating FROM authors";
    String insertAuthor = "INSERT INTO authors(full_name, phone, email, rating) VALUES(?, ?, ?, ?)";
    ArrayList<Author> authors = new ArrayList<>();

    public AuthorServlet() throws FileNotFoundException, IOException {
        prop = new ConnectionProperty();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("new".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/author/form.jsp").forward(request, response);
            return;
        }

        if ("edit".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            EmpConnBuilder builder = new EmpConnBuilder();
            try (Connection conn = builder.getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT id, full_name, phone, email, rating FROM authors WHERE id = ?")) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Author author = new Author(
                        rs.getLong("id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDouble("rating")
                    );
                    request.setAttribute("author", author);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("/WEB-INF/views/author/form.jsp").forward(request, response);
            return;
        }

        if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            EmpConnBuilder builder = new EmpConnBuilder();
            try (Connection conn = builder.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM authors WHERE id = ?")) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("authors");
            return;
        }

        EmpConnBuilder builder = new EmpConnBuilder();
        try (Connection conn = builder.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectAllAuthors)) {
            authors.clear();
            while (rs.next()) {
                authors.add(new Author(
                    rs.getLong("id"),
                    rs.getString("full_name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getDouble("rating")
                ));
            }
            request.setAttribute("authors", authors);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/views/author/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String ratingStr = request.getParameter("rating");

        Double rating = ratingStr == null || ratingStr.isEmpty() ? 0.0 : Double.parseDouble(ratingStr);

        EmpConnBuilder builder = new EmpConnBuilder();

        if (idParam != null && !idParam.isEmpty()) {
            Long id = Long.parseLong(idParam);
            try (Connection conn = builder.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE authors SET full_name=?, phone=?, email=?, rating=? WHERE id=?")) {
                ps.setString(1, fullName);
                ps.setString(2, phone);
                ps.setString(3, email);
                ps.setDouble(4, rating);
                ps.setLong(5, id);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try (Connection conn = builder.getConnection();
                 PreparedStatement ps = conn.prepareStatement(insertAuthor)) {
                ps.setString(1, fullName);
                ps.setString(2, phone);
                ps.setString(3, email);
                ps.setDouble(4, rating);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("authors");
    }
}