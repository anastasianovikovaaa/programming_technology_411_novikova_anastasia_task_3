package novikova_task_3;

import java.math.BigDecimal;
import java.sql.Blob;

public class Account {
    private String id;
    private int client_id;
    private BigDecimal amount;
    private String accCode;

    public Account(String id, int client_id, BigDecimal amount, String accCode) {
        this.id = id;
        this.client_id = client_id;
        this.amount = amount;
        this.accCode = accCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccCode() {
        return accCode;
    }

    public void setAccCode(String accCode) {
        this.accCode = accCode;
    }
}
