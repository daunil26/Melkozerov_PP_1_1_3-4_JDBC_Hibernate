package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class Util {
    private static String username = "root";
    private static String password = "postgres";
    private static String connectionUrl = "jdbc:mysql://localhost:3306/kata_1";
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", connectionUrl);
            prop.setProperty("hibernate.connection.username", username);
            prop.setProperty("hibernate.connection.password", password);
            prop.setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");
            try {
                sessionFactory = new Configuration().addProperties(prop)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return sessionFactory;
    }
}
