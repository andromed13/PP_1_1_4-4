package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS userss (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), lastname VARCHAR(50), age INT)";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS userss";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String save = "INSERT INTO userss (name, lastName, age ) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(save);) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем — " +name+ " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void removeUserById(long id) {
        String remove = "DELETE FROM userss WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(remove);) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String getAll = "SELECT * FROM userss";
        List <User> userss = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAll);
            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userss.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userss;
    }

    public void cleanUsersTable() {
        String cleanTable = "DELETE FROM userss";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(cleanTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void closeCon () {
        try {
            connection.close();
            System.out.println("Соединение с БД закрыто");
        } catch (SQLException e) {
            System.out.println("При закрытии соединения произошла ошибка!");
            throw new RuntimeException(e);
        }
    }
}
