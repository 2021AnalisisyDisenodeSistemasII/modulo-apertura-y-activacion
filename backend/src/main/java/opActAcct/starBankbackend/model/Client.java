package opActAcct.starBankbackend.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Objeto tipo cliente
 * Extendiendo esta clase se pueden crear los tipos de clientes que se quieran
 */
public abstract class Client {

    //Parámetros del cliente
    protected String client_id;
    protected ArrayList accounts;
    protected String phone;
    protected String client_name;
    protected String client_occupation;
    protected String client_address;

    //Constructores de cliente
    public Client(String client_id, ArrayList accounts, String phone, String client_name, String client_occupation, String client_address) {
        this.accounts = accounts;
        this.phone = phone;
        this.client_name = client_name;
        this.client_occupation = client_occupation;
        this.client_address = client_address;
        this.client_id = client_id;
    }

    //Getters y setters

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public String getOccupation() {
        return client_occupation;
    }

    public void setOccupation(String occupation) {
        this.client_occupation = occupation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList accounts) {
        this.accounts = accounts;
    }

}
