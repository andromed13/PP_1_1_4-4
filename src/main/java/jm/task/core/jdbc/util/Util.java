package jm.task.core.jdbc.util;

import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection () {
        Connection connection = null;
        try  {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соединение с БД создано успешно");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Возникла ошибка при соединении");
        }
        return connection;
    }
}
