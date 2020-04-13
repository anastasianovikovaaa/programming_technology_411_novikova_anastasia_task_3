package novikova_task_3;


//jdbc:sqlite::memory:
//C:\Users\Настя\Desktop\programming_technology_411_novikova_anastasia_task_3\lib

import novikova_task_3.db.DbCreator;

public class Main {
    private static final String FILE_NAME = "test.db";
    private static String url = "jdbc:sqlite:C:/Users/Настя/Desktop/programming_technology_411_novikova_anastasia_task_3/sqlite/db/" + FILE_NAME;

    public static void main(String[] args) {

        BankOperations bankOperations = new BankOperations(url);
        boolean temp = true;
        while (temp){
            temp = bankOperations.makeOperation();
        }
    }





}
