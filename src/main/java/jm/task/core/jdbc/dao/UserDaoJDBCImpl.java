package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    Connection connection;

    {
        connection = Util.getConnection();
    }

    public void createUsersTable() {                                                      // создание таблицы
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" +
                    "(id MEDIUMINT not null auto_increment," +
                    "name VARCHAR(50)," +
                    "lastname VARCHAR(50)," +
                    "age TINYINT,"
                    + "PRIMARY KEY (id))");
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {                                        // удаление Таблицы
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {      // Добавление User в таблицу

        String sql = "INSERT INTO users(name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        }
    }


    public void removeUserById(long id) throws SQLException {                               //Удаление User по  ID
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с ID – " + id + " удален из базы данных");
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {                                           // получение всех Users
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, lastname, age FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {                                      // Удаление ALL Users из таблицы
        String sql = "TRUNCATE TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
