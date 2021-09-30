package opActAcct.starBankbackend.model;

import java.util.ArrayList;

public class CurrentAccount extends Account{

    public CurrentAccount(String account_id, String client_id, String sucursal_id, boolean status, Float balance, ArrayList transactions, String creation_date) {
        super(account_id, client_id, sucursal_id, status, balance, transactions, creation_date);
    }

    public CurrentAccount() {
        super();
    }

}
