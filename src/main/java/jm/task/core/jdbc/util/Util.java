package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String USER = "root";
    private static final String PASSWORD = "merry crisis";
    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/pepe337db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static Connection connection = null;

    private SessionFactory sessionFactory = null;

    public Util() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(false);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException("Ошибка подключения к БД", e);
            }
        }
        return connection;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                configuration.setProperty("hibernate.connection.driver_class", JDBC_DRIVER);
                configuration.setProperty("hibernate.connection.url", URL);
                configuration.setProperty("hibernate.connection.username", USER);
                configuration.setProperty("hibernate.connection.password", PASSWORD);
                configuration.setProperty("hibernate.dialect",
                        "org.hibernate.dialect.MySQL8Dialect");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.hbm2ddl.auto", "none");
                configuration.setProperty("hibernate.current_session_context_class",
                        "thread");

                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration.buildSessionFactory();

            } catch (Exception e) {
                throw new RuntimeException("Ошибка инициализации Hibernate", e);
            }
        }
        return sessionFactory;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при закрытии соединения", e);
            }
        }
    }
}