package bo.ucb.edu.ingsoft.model;

import java.util.Date;

public class Transaction {
    private Integer txId;
    private Date txDate;
    private String txHost;
    private Date txUpdate;

    public Transaction() {
    }

    public Integer getTxId() {
        return txId;
    }

    public void setTxId(Integer txId) {
        this.txId = txId;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    public String getTxHost() {
        return txHost;
    }

    public void setTxHost(String txHost) {
        this.txHost = txHost;
    }

    public Date getTxUpdate() {
        return txUpdate;
    }

    public void setTxUpdate(Date txUpdate) {
        this.txUpdate = txUpdate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "txId=" + txId +
                ", txDate=" + txDate +
                ", txHost='" + txHost + '\'' +
                ", txUpdate=" + txUpdate +
                '}';
    }
}
