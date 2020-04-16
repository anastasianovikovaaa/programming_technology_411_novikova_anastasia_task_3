package novikova_task_3.db;

import java.sql.*;

public class DbCreator {

    public static void createNewDatabase(String url) {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createUserTable(String url) {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE UserTable (\n" +
                "    id_user INTEGER PRIMARY KEY,\n" +
                "    login TEXT NOT NULL UNIQUE,\n" +
                "    password TEXT NOT NULL,\n" +
                "    address TEXT NOT NULL,\n" +
                "    phone TEXT NOT NULL UNIQUE\n" +
                ");\n";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createAccountTable(String url) {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE Account (\n" +
                "    id BLOB PRIMARY KEY DEFAULT (randomblob(16)),\n" +
                "    client_id      INTEGER NOT NULL,\n" +
                "    amount DECIMAL(7,2) DEFAULT (0.00),\n" +
                "    accCode TEXT NOT NULL,\n" +
                "        FOREIGN KEY (client_id)\n" +
                "        REFERENCES UserTable (id_user)\n" +
                ");\n";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createHistoryOperations(String url) {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE HistoryOperations (\n" +
                "    id_history INTEGER PRIMARY KEY,\n" +
                "    operation TEXT NOT NULL,\n" +
                "    accountFrom TEXT,\n" +
                "    accountTo TEXT NOT NULL,\n" +
                "    date TEXT NOT NULL"+
                "    amount TEXT NOT NULL"+
                ");\n";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
