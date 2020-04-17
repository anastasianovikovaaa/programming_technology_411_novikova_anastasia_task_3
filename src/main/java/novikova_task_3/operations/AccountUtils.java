package novikova_task_3.operations;

import novikova_task_3.entities.Account;
import novikova_task_3.entities.HistoryOperations;
import novikova_task_3.entities.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static novikova_task_3.operations.HistoryUtils.addToHistory;

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

    public static void transferMoney(Connection connection, User user) {
        if (user == null) {
            System.out.println("Please, log in");
            return;
        }
        Map<String, Account> accounts = loadAccounts(connection, user.getId());
        System.out.println("Enter the currency of your account:");
        String currency = in.nextLine();
        Account accountFrom = accounts.get(currency);
        if(accountFrom == null){
            System.out.println("Account was not found.");
            return;
        }
        System.out.println("Enter the phone number:");
        String phone = in.nextLine();
        String accCode = accountFrom.getAccCode();
        Map<String,Account> accMap = loadAccounts(connection, phone);
        if(accMap == null ||  accCode == null){
            System.out.println("This phone number is not registered.");
            return;
        }
        Account accountTo = accMap.get(accCode);
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
        addToHistory(connection, new HistoryOperations("Transfering",accountFrom.getId(), accountTo.getId(), new Date(), sum.toString()));
    }

    public static void makeDeposit(Connection connection, User user) {
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
        System.out.println("Enter the currency of your account:");
        String cur = in.nextLine();
        Account account = accounts.get(cur);
        if(account == null){
            System.out.println("Account was not found.");
            return;
        }
        System.out.println("Choose the currency of your deposit:" +
                "\n 1 - RUB;" +
                "\n 2 - USD;" +
                "\n 3 - EUR;");
        String choose = in.nextLine();
        System.out.println("Enter the amount of the deposit:");
        BigDecimal amount = in.nextBigDecimal();
        switch (choose){
            case "1":
                if(cur.equals("USD")){
                    amount = Convertation.СurrencyToRubles(amount, Convertation.getRateUSD());
                }else
                if(cur.equals("EUR")){
                    amount = Convertation.СurrencyToRubles(amount, Convertation.getRateEUR());
                }
                break;
            case "2":
                if(!cur.equals("USD")) {
                    if( cur.equals("RUB"))
                        amount = Convertation.RublesToCurrency(amount, Convertation.getRateUSD());
                    else
                        if(cur.equals("EUR"))
                            amount = Convertation.CurrencyToCurrency(amount, Convertation.getRateEUR(), Convertation.getRateUSD());
                }
                break;
            case "3":
                if(!cur.equals("EUR")){
                    if(cur.equals("RUB"))
                        amount = Convertation.RublesToCurrency(amount, Convertation.getRateEUR());
                    else
                    if(cur.equals("USD"))
                        amount = Convertation.CurrencyToCurrency(amount, Convertation.getRateUSD(), Convertation.getRateEUR());
                }
                break;
            default:
                System.out.println("Wrong choice!");
                return;
        }
        account.setAmount(account.getAmount().add(amount));
        updateAccount(connection, account);
        user.addToHistory(String.format("Account: %s, depositing amount: %s, total sum: %s", account.getId(), amount,
                account.getAmount()));
        addToHistory(connection, new HistoryOperations("Deposit",account.getId(), account.getId(), new Date(), amount.toString()));
    }

    public static Map<String, Account> loadAccounts(Connection connection, String phone){
        String sql = "SELECT id_user from UserTable where phone = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Map<String, Account> accountMap = loadAccounts(connection, resultSet.getInt("id_user"));
                return accountMap;
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
                userAccounts.put(account.getAccCode(),account);
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

