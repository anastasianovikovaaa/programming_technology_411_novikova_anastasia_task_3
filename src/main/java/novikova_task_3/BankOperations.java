package novikova_task_3;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class BankOperations {

    private String url;
    private Scanner in = new Scanner(System.in);
    private User user;
    Connection connection;

    public BankOperations(String url){
        this.url = url;
        connection = getConnection(url);
    }

    public void chooseOperation(String operation){

        switch (operation){
            case ("1"):
                register();
                break;
            case("2"):
                login();
                break;
            case ("3"):
                createAccount();
                break;
            case ("4"):
                makeDeposit();
                break;
            case ("5"):
                transferMoney();
                break;
            case ("6"):
                showHistory();
                break;
        }
    }

    private  void showHistory() {

    }

    private  void transferMoney() {
        Map<String, Account> accounts = loadAccounts(user.getId());
        System.out.println("Enter the id of account:");
        String id = in.nextLine();
        Account accountFrom = accounts.get(id);
        if(accountFrom == null){
            System.out.println("Account was not found.");
            return;
        }
        System.out.println("Enter the phone number:");
        String phone = in.nextLine();
        Account accountTo = loadAccounts(phone).get(accountFrom.getAccCode());
        if(accountTo == null){
            System.out.println("User does not has account with this accCode");
            return;
        }
        System.out.println("Enter sum of transfer:");
        BigDecimal sum = in.nextBigDecimal();
        accountFrom.setAmount(accountFrom.getAmount().subtract(sum));
        updateAccount(accountFrom);
        accountTo.setAmount(accountTo.getAmount().add(sum));
        updateAccount(accountTo);
    }

    private  void makeDeposit() {
        if(user == null){
            System.out.println("Please, log in");
            return;
        }
        Map<String,Account> accounts = loadAccounts(user.getId());
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
        updateAccount(account);
    }

    private  void createAccount() {
        if(user == null){
            System.out.println("Please, log in");
            return;
        }
        System.out.println("Enter the accCode:");
        String accCode = in.nextLine();
        String sql = "INSERT INTO AccountTable(client_id,accCode) VALUES(?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setString(2,accCode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void login() {
        System.out.println("Enter login or phone number:");
        String login = in.nextLine();
        System.out.println("Enter password:");
        String password = in.nextLine();

        String sql = "SELECT * FROM UserTable WHERE (login = ? OR phone = ?) AND password = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,login);
            preparedStatement.setString(3,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new User(resultSet.getInt("id_user"),resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("address"), resultSet.getString("phone"));
            }
            else{
                System.out.println("User was not found");
            }
        } catch (SQLException e) {
            System.out.println("Error while login executing. " + e);
        }
    }

    private  void register() {
        System.out.println("Enter the login:");
        String login = in.nextLine();
        System.out.println("Enter the password:");
        String password = in.nextLine();
        System.out.println("Enter email address:");
        String address = in.nextLine();
        System.out.println("Enter phone:");
        String phone = in.nextLine();
        insertUser(login,password,address,phone);
    }

    public Connection getConnection(String url) {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(String.format("Error while creating connection. %s", e));
        }
        return null;
    }

    private void insertUser(String login, String password, String address, String phone) {
        String sql = "INSERT INTO UserTable(login,password,address,phone) VALUES(?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,address);
            preparedStatement.setString(4,phone);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<String,Account> loadAccounts(String phone){
        String sql = "SELECT id_user from UserTable where phone = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return loadAccounts(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Map<String,Account> loadAccounts(int id) {

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

    private void updateAccount(Account account){
        String sql = "UPDATE Account SET amount = ? WHERE id = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, account.getId());
            preparedStatement.setBigDecimal(2,account.getAmount());
            System.out.println("Deposit was executed successfully.");
        } catch (SQLException e) {
            System.out.println("Error while updationg the amount of the account. " + e);
        }
    }
}
