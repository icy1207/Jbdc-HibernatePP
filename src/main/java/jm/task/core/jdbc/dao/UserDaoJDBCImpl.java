package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.management.PersistentMBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS users (" + "id SERIAL PRIMARY KEY, " + "name VARCHAR(50) NOT NULL, " + "lastName VARCHAR(50) NOT NULL, " + "age SMALLINT NOT NULL" + ");";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_SQL);
            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        final String DROP_SQL = "DROP TABLE users";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_SQL);
            System.out.println("Таблица успешно удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String SAVE_USER = "INSERT INTO users.users(name, lastname, age) VALUES (?, ? ,?)";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Студент с именем" + " " + name + " " + "успешно добавлен.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        final String REMOVE_ID = "DELETE FROM users.users WHERE id = ?";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_ID)) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) System.out.println("Объект удалён");
            else System.out.println("Объект не найден");
        } catch (SQLException e) {
        }
    }

    public List<User> getAllUsers() {
        final String GET_ALL = "SELECT * FROM users.users";
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                User user = new User(set.getString("name"), set.getString("lastName"), set.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        final String CLEAR_ALL_USERS = "DELETE  FROM users.users";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate(CLEAR_ALL_USERS);
            System.out.println("Все юзеры удалены,таблица пуста.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
