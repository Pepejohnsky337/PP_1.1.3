package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Sasha", "Kisliy", (byte) 30);
        userService.saveUser("Vladimir", "Vladimirov", (byte) 40);
        userService.saveUser("Sveta", "Ustavshaya", (byte) 50);

        userService.removeUserById(0);

        List<User> users = userService.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println(i + ". ID: " + user.getId() +
                    ", Name: " + user.getName() +
                    ", LastName: " + user.getLastName() +
                    ", Age: " + user.getAge());
        }

        System.out.println(users.size());
        userService.getAllUsers();

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}