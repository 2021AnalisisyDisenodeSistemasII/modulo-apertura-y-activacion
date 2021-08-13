package opActAcct.starBankbackend.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CompanyClient extends Client{
    public CompanyClient(String client_id, ArrayList accounts, String phone, String client_name, String client_occupation, String client_address) {
        super(client_id, accounts, phone, client_name, client_occupation, client_address);
    }
}
