package opActAcct.starBankbackend.repository.repositoryJson;

import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.repository.JsonImplementation;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;
import opActAcct.starBankbackend.repository.interfaces.IClientRepository;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ClientJSON extends JsonImplementation implements IClientRepository {

    private static ArrayList<String> listClientFileNames =new ArrayList<>() ;
    private static HashMap<String, LinkedTreeMap<String,Object>> clients= new HashMap<>();

    public ClientJSON() {
        listClientFileNames.add("natural_clients.json");
        listClientFileNames.add("company_clients.json");
    }

    @Override
    public abstract void add(Object objectToWrite, String fileName) throws DuplicateKeyException;

    @Override
    public abstract void update(Object clientObject, String fileName) throws KeyDoesNotExistException;

    @Override
    public abstract void associateAccountToClient(String account_id, String client_id) throws  KeyDoesNotExistException;

    @Override
    public abstract Object findClient(String id_client, String fileName) throws KeyDoesNotExistException;

    /*
    @Override
    public Object findClient(String id_client) throws KeyDoesNotExistException {
        for (String nameFile: listClientFileNames) {
            clients = (HashMap) readJson(nameFile);
            if (clients.containsKey(id_client)){
                return clients;
            }
        }
        throw new KeyDoesNotExistException(id_client);
    }
    */

}
