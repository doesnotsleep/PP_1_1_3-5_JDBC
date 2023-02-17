package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String DB = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/MySQL";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Connection OK");
        return connection;
    }

}
