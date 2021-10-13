package opActAcct.starBankbackend.model;

import java.util.ArrayList;

/**
 * Objeto tipo cuenta
 * Extendiendo esta clase se pueden crear los tipos de cuenta que se quieran
 */
public abstract class Account {

    //Parámetros que tendrán las cuentas
    protected String account_id;
    protected String client_id;
    protected String sucursal_id;
    protected boolean status;
    protected Float balance;
    protected ArrayList transactions;
    protected String creation_date;

    //Constructores
    public Account(String account_id,
                   String client_id,
                   String sucursal_id,
                   boolean status,
                   Float balance,
                   ArrayList transactions,
                   String creation_date) {
        this.account_id = account_id;
        this.client_id = client_id;
        this.sucursal_id = sucursal_id;
        this.status = status;
        this.balance = balance;
        this.transactions = transactions;
        this.creation_date = creation_date;
    }

    public Account() {
    }

    public Account(String account_id, String client_id, Float balance, Boolean status) {
        this.account_id = account_id;
        this.client_id = client_id;
        this.balance = balance;
        this.status = status;
    }

    //Getters y setters

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getSucursal_id() {
        return sucursal_id;
    }

    public void setSucursal_id(String sucursal_id) {
        this.sucursal_id = sucursal_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public ArrayList getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList transactions) {
        this.transactions = transactions;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
