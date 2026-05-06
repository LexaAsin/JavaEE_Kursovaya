package test.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.EmpConnBuilder;

import java.sql.Connection;

public class ConnectionTest {

    @Test
    public void testConnection() {
        try {
            Connection conn = new EmpConnBuilder().getConnection();
            assertNotNull("Подключение к БД не должно быть null", conn);
            assertFalse("Соединение должно быть открыто", conn.isClosed());
            conn.close();
        } catch (Exception e) {
            fail("Ошибка подключения к БД: " + e.getMessage());
        }
    }
}