package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String USER = "root";
    private static final String PASSWORD = "merry crisis";
    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/pepe337db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException("Ошибка подключения к БД", e);
            }
        }
        return connection;
    }
}