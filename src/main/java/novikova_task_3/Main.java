package novikova_task_3;


//jdbc:sqlite::memory:
//C:\Users\Настя\Desktop\programming_technology_411_novikova_anastasia_task_3\lib

import java.sql.*;

public class Main {
    private static final String FILE_NAME = "test.db";
    private static String url = "jdbc:sqlite:C:/Users/Настя/Desktop/programming_technology_411_novikova_anastasia_task_3/sqlite/db/" + FILE_NAME;

    public static void createNewDatabase() {
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

    public static void main(String[] args) {
        //createNewDatabase();
       // createUserTable();
       // createAccountTable();
    }

    public static void createUserTable() {
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

    public static void createAccountTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE AccountTable (\n" +
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



}
