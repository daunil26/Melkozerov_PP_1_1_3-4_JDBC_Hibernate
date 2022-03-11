package jm.task.core.jdbc.model.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    static String username = "root";
    static String password = "postgres";
    static String connectionUrl = "jdbc:mysql://localhost:3306/kata_1";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("create table if not exists Users (id INTEGER not null AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INTEGER, PRIMARY KEY (id))").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id = :userId");
            query.setParameter("userId", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = new Util().getSessionFactory().openSession()) {
            users = (List<User>) session.createQuery("From User").list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = new Util().getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
