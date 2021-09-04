package opActAcct.starBankbackend.model;

import java.util.ArrayList;

public class SavingAccount extends Account{

    public SavingAccount(String account_id, String client_id, String sucursal_id, boolean status, Float balance, ArrayList transactions, String create_date) {
        super(account_id, client_id, sucursal_id, status, balance, transactions, create_date);
    }

}
