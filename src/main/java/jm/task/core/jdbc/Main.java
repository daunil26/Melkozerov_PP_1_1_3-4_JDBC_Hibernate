package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Danil", "Melkozerov", (byte) 23);
        userService.saveUser("Andrey", "Agapitov", (byte) 35);
        userService.saveUser("Igor", "Petrov", (byte) 18);
        userService.saveUser("Anna", "Ivanova", (byte) 46);
        List<User> userList = userService.getAllUsers();
        for (User user:userList) {
            System.out.println(user.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
