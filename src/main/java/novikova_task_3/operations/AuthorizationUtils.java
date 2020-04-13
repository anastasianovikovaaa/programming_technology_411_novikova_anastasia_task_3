package novikova_task_3.operations;

import novikova_task_3.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AuthorizationUtils {

    private static Scanner in = new Scanner(System.in);

    public static User login(Connection connection) {
        System.out.println("Enter login or phone number:");
        String login = in.nextLine();
        System.out.println("Enter password:");
        String password = in.nextLine();
        String sql = "SELECT * FROM UserTable WHERE (login = ? OR phone = ?) AND password = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt("id_user"), resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("address"), resultSet.getString("phone"));
            } else {
                System.out.println("User was not found");
            }
        } catch (SQLException e) {
            System.out.println("Error while login executing. " + e);
        }
        return null;
    }

    public static void register(Connection connection) {
        System.out.println("Enter the login:");
        String login = in.nextLine();
        System.out.println("Enter the password:");
        String password = in.nextLine();
        System.out.println("Enter email address:");
        String address = in.nextLine();
        System.out.println("Enter phone:");
        String phone = in.nextLine();
        insertUser(connection, login, password, address, phone);
    }

    private static void insertUser(Connection connection, String login, String password, String address, String phone) {
        String sql = "INSERT INTO UserTable(login,password,address,phone) VALUES(?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, phone);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
