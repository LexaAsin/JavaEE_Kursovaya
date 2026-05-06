package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/testdb")
public class TestDbServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/plain");
        StringBuilder sb = new StringBuilder();
        
        try {
            Class.forName("org.postgresql.Driver");
            sb.append("✅ Драйвер загружен успешно!\n");
            
            // Попробуем подключиться, используя твои параметры
            String url = "jdbc:postgresql://localhost:5432/library";
            String user = "postgres";
            String password = "";  // если пароль есть – укажи
            
            java.sql.Connection conn = DriverManager.getConnection(url, user, password);
            sb.append("✅ Соединение с БД установлено!\n");
            conn.close();
            
        } catch (ClassNotFoundException e) {
            sb.append("❌ Драйвер не найден!\n");
            sb.append(e.getMessage());
        } catch (SQLException e) {
            sb.append("❌ Ошибка подключения к БД:\n");
            sb.append(e.getMessage());
        }
        
        response.getWriter().print(sb.toString());
    }
}