package opActAcct.starBankbackend.repository.repositoryJson;

import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.model.CurrentAccount;
import opActAcct.starBankbackend.repository.interfaces.IClientRepository;
import opActAcct.starBankbackend.repository.interfaces.ICurrentAccountRepository;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

import java.io.*;
import java.util.*;


public class CurrentAccountJSON extends AccountJSON implements ICurrentAccountRepository {

    private static String fileName = "current_accounts.json";
    private static HashMap<String, LinkedTreeMap<String,Object>> accounts= new HashMap<>();

    public CurrentAccountJSON() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
        }
        catch (FileNotFoundException ex) {    //Excepción de cuando no encuentra un archivo con el nombre alojado en la variable "fileName"
            //Se crea un archivo con el nombre alojado en la variable "fileName"
            File file = new File(fileName);
        }
        catch (IOException ex) {
            System.out.println("Problema, mirar error IOE");
        }
    }

    @Override
    public void createNewAccount(String account_id, String client_id, String sucursal_id) throws DuplicateKeyException, KeyDoesNotExistException {
        // (1) Verifica que el cliente si exista.
        IClientRepository clientRepository = new CompanyClientJSON();
        try{
            Object client = clientRepository.findClient(client_id, "company_clients.json");
        }catch (KeyDoesNotExistException kne){
            throw new KeyDoesNotExistException(client_id);
        }

        // (2) Verifica que esa account_id (NIT) no exista.
        if(accounts.containsKey( account_id )){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(account_id);
        }
        Date fecha = new Date();

        // (3) Crea una nueva cuenta de CORRIENTE
        CurrentAccount account = new CurrentAccount(account_id, client_id, sucursal_id, false, Float.parseFloat("0"), new ArrayList(), fecha.toString());

        // (4) Sobreescribe el Json con todas las cuentas que aloja el Diccionario llamado account
        addToJson(account, fileName);

        // (5) Asocia una cuenta al cliente.
        clientRepository.associateAccountToClient(account_id, client_id);

    }


}