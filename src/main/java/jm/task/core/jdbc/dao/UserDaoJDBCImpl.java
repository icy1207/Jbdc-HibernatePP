package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.management.PersistentMBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CreateSql = "CREATE TABLE IF NOT EXISTS users (" + "id SERIAL PRIMARY KEY, " + "name VARCHAR(50) NOT NULL, " + "lastName VARCHAR(50) NOT NULL, " + "age SMALLINT NOT NULL" + ");";
    private static final String DropSql = "DROP TABLE users";
    private static final String saveUser = "INSERT INTO users.users(name, lastname, age) VALUES (?, ? ,?)";
    private static final String removeId = "DELETE FROM users.users WHERE id = ?";
    private static final String getAll = "SELECT * FROM users.users";
    private static final String clearAllUsers = "DELETE  FROM users.users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(CreateSql);
            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(DropSql);
            System.out.println("Таблица успешно удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {
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
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(removeId)) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) System.out.println("Объект удалён");
            else System.out.println("Объект не найден");
        } catch (SQLException e) {
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(getAll)) {
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
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate(clearAllUsers);
            System.out.println("Все юзеры удалены,таблица пуста.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
