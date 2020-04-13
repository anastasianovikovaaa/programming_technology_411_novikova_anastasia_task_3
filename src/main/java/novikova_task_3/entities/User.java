package novikova_task_3.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String login;
    private String password;
    private String address;
    private String phone;
    private List<String> history;

    public User(int id, String login, String password, String address, String phone) {
        this.login = login;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.history = new ArrayList<String>();
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void printHistory(){
        System.out.println("Showing history:");
        for(String item: history){
            System.out.println(item);
        }
    }

    public void addToHistory(String str){
        history.add(str);
    }
}
