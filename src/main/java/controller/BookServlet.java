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
import domain.Book;

import java.sql.SQLException;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ConnectionProperty prop;
    String selectAllBooks = "SELECT b.id, b.title, b.genre, b.year, a.id as author_id, a.full_name as author_name " +
                            "FROM books b LEFT JOIN authors a ON b.author_id = a.id";
    String insertBook = "INSERT INTO books (title, genre, year, author_id) VALUES (?, ?, ?, ?)";
    ArrayList<Book> books = new ArrayList<>();
    ArrayList<Author> authorsList = new ArrayList<>();

    public BookServlet() throws FileNotFoundException, IOException {
        prop = new ConnectionProperty();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "list";

        // Загружаем список авторов для выпадающего списка (всегда)
        try (Connection conn = new EmpConnBuilder().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rsAuthors = stmt.executeQuery("SELECT id, full_name FROM authors ORDER BY full_name")) {
            authorsList.clear();
            while (rsAuthors.next()) {
                Author author = new Author();
                author.setId(rsAuthors.getLong("id"));
                author.setFullName(rsAuthors.getString("full_name"));
                authorsList.add(author);
            }
            request.setAttribute("authors", authorsList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ("list".equals(action)) {
            // Обычный список книг
            try (Connection conn = new EmpConnBuilder().getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectAllBooks)) {
                books.clear();
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getLong("id"));
                    book.setTitle(rs.getString("title"));
                    book.setGenre(rs.getString("genre"));
                    book.setYear(rs.getInt("year"));

                    Author author = new Author();
                    author.setId(rs.getLong("author_id"));
                    author.setFullName(rs.getString("author_name"));
                    book.setAuthor(author);

                    books.add(book);
                }
                request.setAttribute("books", books);
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("/WEB-INF/views/book/list.jsp").forward(request, response);
            return;
        }

        if ("new".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/book/form.jsp").forward(request, response);
            return;
        }

        if ("edit".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            try (Connection conn = new EmpConnBuilder().getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT id, title, genre, year, author_id FROM books WHERE id = ?")) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getLong("id"));
                    book.setTitle(rs.getString("title"));
                    book.setGenre(rs.getString("genre"));
                    book.setYear(rs.getInt("year"));
                    Long authorId = rs.getLong("author_id");
                    for (Author a : authorsList) {
                        if (a.getId().equals(authorId)) {
                            book.setAuthor(a);
                            break;
                        }
                    }
                    request.setAttribute("book", book);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("/WEB-INF/views/book/form.jsp").forward(request, response);
            return;
        }

        if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            try (Connection conn = new EmpConnBuilder().getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM books WHERE id = ?")) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("books");
            return;
        }

        // Если action не распознан — показываем список
        response.sendRedirect("books?action=list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        // Обработка создания нового автора из модального окна
        if ("createAuthor".equals(action)) {
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String ratingStr = request.getParameter("rating");
            Double rating = ratingStr == null || ratingStr.isEmpty() ? 0.0 : Double.parseDouble(ratingStr);
            
            Author newAuthor = new Author();
            newAuthor.setFullName(fullName);
            newAuthor.setPhone(phone);
            newAuthor.setEmail(email);
            newAuthor.setRating(rating);
            
            // Сохраняем автора и получаем ID
            Long authorId = saveAuthor(newAuthor);
            
            // Возвращаем JSON для JavaScript
            response.setContentType("application/json");
            response.getWriter().write("{\"id\":" + authorId + ", \"fullName\":\"" + newAuthor.getFullName() + "\"}");
            return;
        }
        
        // Обычное сохранение/обновление книги
        String idParam = request.getParameter("id");
        String title = request.getParameter("title");
        String genre = request.getParameter("genre");
        int year = Integer.parseInt(request.getParameter("year"));
        Long authorId = Long.parseLong(request.getParameter("authorId"));

        EmpConnBuilder builder = new EmpConnBuilder();

        if (idParam != null && !idParam.isEmpty()) {
            Long id = Long.parseLong(idParam);
            try (Connection conn = builder.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE books SET title=?, genre=?, year=?, author_id=? WHERE id=?")) {
                ps.setString(1, title);
                ps.setString(2, genre);
                ps.setInt(3, year);
                ps.setLong(4, authorId);
                ps.setLong(5, id);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try (Connection conn = builder.getConnection();
                 PreparedStatement ps = conn.prepareStatement(insertBook)) {
                ps.setString(1, title);
                ps.setString(2, genre);
                ps.setInt(3, year);
                ps.setLong(4, authorId);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("books?action=list");
    }
    
    private Long saveAuthor(Author author) {
        String sql = "INSERT INTO authors (full_name, phone, email, rating) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = new EmpConnBuilder().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getFullName());
            ps.setString(2, author.getPhone());
            ps.setString(3, author.getEmail());
            ps.setDouble(4, author.getRating());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}