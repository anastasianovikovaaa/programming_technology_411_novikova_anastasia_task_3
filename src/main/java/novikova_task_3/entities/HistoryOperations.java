package novikova_task_3.entities;

import java.util.Date;

public class HistoryOperations {
    private String id;
    private String operation;
    private String accountFrom;
    private String accountTo;
    private Date date;
    private String amount;

    public HistoryOperations(String operation, String accountFrom, String accountTo, Date date, String amount) {
        this.operation = operation;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.date = date;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
