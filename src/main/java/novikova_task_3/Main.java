package novikova_task_3;


//jdbc:sqlite::memory:
//C:\Users\Настя\Desktop\programming_technology_411_novikova_anastasia_task_3\lib

public class Main {
    private static final String FILE_NAME = "test.db";
    private static String url = "jdbc:sqlite:C:/Users/Настя/Desktop/programming_technology_411_novikova_anastasia_task_3/sqlite/db/" + FILE_NAME;

    public static void main(String[] args) {
//        DbCreator.createNewDatabase(url);
//        DbCreator.createUserTable(url);
//        DbCreator.createAccountTable(url);
//        DbCreator.createHistoryOperations(url);
        BankOperations bankOperations = new BankOperations(url);
        boolean temp = true;
        while (temp){
            temp = bankOperations.makeOperation();
        }
    }





}
