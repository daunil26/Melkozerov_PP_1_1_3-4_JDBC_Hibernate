package jm.task.core.jdbc.model.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    static String username = "root";
    static String password = "postgres";
    static String connectionUrl = "jdbc:mysql://localhost:3306/kata_1";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = new Util().getConnection(username, password, connectionUrl);
        if (connection == null) {
            return;
        }
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("create table if not exists UsersTable (id INTEGER not null AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INTEGER, PRIMARY KEY (id))");
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        Connection connection = new Util().getConnection(username, password, connectionUrl);
        if (connection == null) {
            return;
        }
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("drop table if exists UsersTable");
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = new Util().getConnection(username, password, connectionUrl);
        if (connection == null) {
            return;
        }
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("insert into UsersTable (name, lastname, age) values (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        Connection connection = new Util().getConnection(username, password, connectionUrl);
        if (connection == null) {
            return;
        }
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM UsersTable WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = new Util().getConnection(username, password, connectionUrl);
        if (connection == null) {
            return users;
        }
        try {
            connection.setAutoCommit(false);
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from UsersTable");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public void cleanUsersTable() {
        Connection connection = new Util().getConnection(username, password, connectionUrl);
        if (connection == null) {
            return;
        }
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE UsersTable");
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы mySQL");
        }
    }
}
