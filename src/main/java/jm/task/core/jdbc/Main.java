package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        //Hibernate:
        List<User> getUsers = null;
        userService.createUsersTable();

        List<User> users = new ArrayList<>();
        users.add(new User("Vlad", "Ledovskikh", (byte) 25));
        users.add(new User("Egorr", "Popou", (byte) 24));
        users.add(new User("Igor", "Vihorkov", (byte) 59));
        users.add(new User("Ivan", "Vtuzov", (byte) 100));

        for (User user : users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        }
        getUsers = userService.getAllUsers();
        getUsers.forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.closeSessionFactory();
    }
}
