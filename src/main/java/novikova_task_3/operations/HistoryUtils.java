package novikova_task_3.operations;

import novikova_task_3.entities.HistoryOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryUtils {
    public static void addToHistory(Connection connection, HistoryOperations operation){
        String sql = "INSERT INTO HistoryOperations(operation,accountFrom,accountTo,date,amount) VALUES(?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, operation.getOperation());
            preparedStatement.setString(2, operation.getAccountFrom());
            preparedStatement.setString(3, operation.getAccountTo());
            preparedStatement.setString(4, operation.getDate().toString());
            preparedStatement.setString(5, operation.getAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getHistory(Connection connection){
        String sql = "SELECT * FROM HistoryOperations";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                System.out.println("Date: " + resultSet.getString("date")
                + "\n Operation: " + resultSet.getString("operation")
                + "\n From: " + resultSet.getString("accountFrom")
                + "\n To: " + resultSet.getString("accountTo")
                + "\n Amount: " + resultSet.getString("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
