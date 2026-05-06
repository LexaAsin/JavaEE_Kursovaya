package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EmpConnBuilder implements ConnectionBuilder {

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ PostgreSQL JDBC Driver загружен!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ PostgreSQL JDBC Driver не найден!");
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
    	String url = "jdbc:postgresql://localhost:5432/library?useUnicode=yes&characterEncoding=UTF-8";
        String login = "lasin";
        String password = "";
        return DriverManager.getConnection(url, login, password);
    }
}