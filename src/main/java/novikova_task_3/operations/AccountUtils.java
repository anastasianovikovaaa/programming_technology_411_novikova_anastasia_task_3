package novikova_task_3.operations;

import novikova_task_3.entities.Account;
import novikova_task_3.entities.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class AccountUtils {

    private static Scanner in = new Scanner(System.in);

    public static void createAccount(Connection connection, User user) {
        if (user == null) {
            System.out.println("Please, log in");
            return;
        }
        System.out.println("Enter the accCode:");
        String accCode = in.nextLine();
        String sql = "INSERT INTO Account(client_id,accCode) VALUES(?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, accCode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static  void transferMoney(Connection connection, User user) {
        Map<String, Account> accounts = loadAccounts(connection, user.getId());
        System.out.println("Enter the id of account:");
        String id = in.nextLine();
        Account accountFrom = accounts.get(id);
        if(accountFrom == null){
            System.out.println("Account was not found.");
            return;
        }
        System.out.println("Enter the phone number:");
        String phone = in.nextLine();
        Account accountTo = Objects.requireNonNull(loadAccounts(connection, phone)).get(accountFrom.getAccCode());
        if(accountTo == null){
            System.out.println("User does not has account with this accCode");
            return;
        }
        System.out.println("Enter sum of transfer:");
        BigDecimal sum = in.nextBigDecimal();
        accountFrom.setAmount(accountFrom.getAmount().subtract(sum));
        updateAccount(connection, accountFrom);
        accountTo.setAmount(accountTo.getAmount().add(sum));
        updateAccount(connection, accountTo);
        user.addToHistory(String.format("Operation: Transfering money: %s, from %s to %s", sum, accountFrom.getId(), accountTo.getId()));
    }

    public static  void makeDeposit(Connection connection, User user) {
        if(user == null){
            System.out.println("Please, log in");
            return;
        }
        Map<String,Account> accounts = loadAccounts(connection, user.getId());
        if(accounts.isEmpty())
        {
            System.out.println("This user has no accounts");
            return;
        }
        System.out.println("Enter the id of account:");
        String id = in.nextLine();
        Account account = accounts.get(id);
        if(account == null){
            System.out.println("Account was not found.");
            return;
        }
        System.out.println("Enter the amount of the deposit:");
        BigDecimal amount = in.nextBigDecimal();
        account.setAmount(account.getAmount().add(amount));
        updateAccount(connection, account);
        user.addToHistory(String.format("Account: %s, depositing amount: %s, total sum: %s", account.getId(), amount,
                account.getAmount()));
    }

    public static Map<String, Account> loadAccounts(Connection connection, String phone){
        String sql = "SELECT id_user from UserTable where phone = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return loadAccounts(connection, resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Map<String,Account> loadAccounts(Connection connection, int id) {

        String sql = "SELECT * from Account where client_id=?";
        Map<String,Account> userAccounts = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Account account = new Account(result.getString("id")
                        , result.getInt("client_id")
                        , new BigDecimal(result.getString("amount"))
                        , result.getString("accCode"));
                userAccounts.put(account.getId(),account);
            }
            if (userAccounts.size() == 0) return new HashMap<>();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return userAccounts;
    }

    public static void updateAccount(Connection connection, Account account){
        String sql = "UPDATE Account SET amount = ? WHERE id = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, account.getId());
            preparedStatement.setBigDecimal(2,account.getAmount());
            System.out.println("Deposit was executed successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updating the amount of the account. " + e);
        }
    }
}
