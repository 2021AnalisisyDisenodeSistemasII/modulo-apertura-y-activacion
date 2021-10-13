package opActAcct.starBankbackend.model;

import java.util.ArrayList;

/**
 * Tipo de cliente: Cliente natural.
 */
public class NaturalClient extends Client{

    public NaturalClient(String client_id, ArrayList accounts, String phone, String client_name, String client_occupation, String client_address) {
        super(client_id, accounts, phone, client_name, client_occupation, client_address);
    }

}
