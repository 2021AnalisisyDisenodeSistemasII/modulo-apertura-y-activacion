package opActAcct.starBankbackend.repository.repositoryJson;

import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.model.*;
import opActAcct.starBankbackend.repository.interfaces.IClientRepository;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

import java.util.*;

public class SavingAccountJSON extends AccountJSON  {

    private static String fileName = "saving_accounts.json";
    private static HashMap<String, LinkedTreeMap<String,Object>> accounts= new HashMap<>();

    public SavingAccountJSON() {

    }

    /**
     *
     * @param client_id
     * @param sucursal_id
     * @param account_id_null Siempre es NULL en esta implementación.
     * @throws DuplicateKeyException
     * @throws KeyDoesNotExistException
     */
    @Override
    public Account createNewAccount(
            String client_id,
            String sucursal_id,
            String account_id_null)
            throws DuplicateKeyException,
            KeyDoesNotExistException {

        // (1) Verifica que el cliente si exista.
        IClientRepository clientRepository = new NaturalClientJSON();
        try{
            clientRepository.findClient(client_id, "natural_clients.json");
        }catch (KeyDoesNotExistException kne){
            throw new KeyDoesNotExistException(client_id);
        }

        // (2) Verifica que esa account_id no exista.
        String account_id = UUID.randomUUID().toString();
        if(accounts.containsKey( account_id )){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(account_id);
        }
        Date fecha = new Date();

        // (3) Crea una nueva cuenta de CORRIENTE
        SavingAccount account = new SavingAccount(account_id, client_id, sucursal_id, false, Float.parseFloat("0"), new ArrayList(), fecha.toString());

        // (4) Sobreescribe el Json con todas las cuentas que aloja el Diccionario llamado account
        add(account, fileName);

        // (5) Asocia una cuenta al cliente.
        clientRepository.associateAccountToClient(account_id, client_id);
        return account;
    }




}
