package jm.task.core.jdbc.model.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = new Util().getConnection().createStatement()) {
            statement.executeUpdate("create table if not exists UsersTable (id INTEGER not null AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INTEGER, PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы mySQL");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = new Util().getConnection().createStatement()) {
            statement.executeUpdate("drop table if exists UsersTable");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы mySQL");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = new Util().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into UsersTable (name, lastname, age) values (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка в записи новой строки таблицы mySQL");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = new Util().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM UsersTable WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка удаления строки БД таблицы mySQL");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = new Util().getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from UsersTable");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка удаления строки БД таблицы mySQL");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = new Util().getConnection().createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE UsersTable");
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы mySQL");
        }
    }
}
