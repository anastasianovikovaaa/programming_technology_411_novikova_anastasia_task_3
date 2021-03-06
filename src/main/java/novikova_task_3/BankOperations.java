package novikova_task_3;

import novikova_task_3.entities.User;
import novikova_task_3.operations.AccountUtils;
import novikova_task_3.operations.AuthorizationUtils;
import novikova_task_3.operations.HistoryUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankOperations {

    private String url;
    private Scanner in = new Scanner(System.in);
    private User user;
    Connection connection;

    public BankOperations(String url){
        this.url = url;
        connection = getConnection(url);
    }

    public boolean makeOperation(){

        System.out.println("Choose the operation: " +
                "\n 1 - Registration;" +
                "\n 2 - Login;" +
                "\n 3 - Create account;" +
                "\n 4 - Make deposit;" +
                "\n 5 - Transfer money;" +
                "\n 6 - Show history;" +
                "\n 7 - Exit;");
        String operation = in.nextLine();
        switch (operation){
            case ("1"):
                AuthorizationUtils.register(connection);
                break;
            case("2"):
                user = AuthorizationUtils.login(connection);
                break;
            case ("3"):
                AccountUtils.createAccount(connection, user);
                break;
            case ("4"):
                AccountUtils.makeDeposit(connection, user);
                break;
            case ("5"):
                AccountUtils.transferMoney(connection, user);
                break;
            case ("6"):
                showHistory();
                break;
            default:
                return false;
        }
        return true;
    }

    private  void showHistory() {
        HistoryUtils.getHistory(connection);
    }


    public Connection getConnection(String url) {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(String.format("Error while creating connection. %s", e));
        }
        return null;
    }
}
