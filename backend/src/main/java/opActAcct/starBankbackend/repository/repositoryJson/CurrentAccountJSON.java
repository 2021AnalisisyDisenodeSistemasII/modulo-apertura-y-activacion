package opActAcct.starBankbackend.repository.repositoryJson;

import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.model.CurrentAccount;
import opActAcct.starBankbackend.repository.interfaces.IClientRepository;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

import java.io.*;
import java.util.*;


public class CurrentAccountJSON extends AccountJSON {

    //Nombre de archivo donde se alojarán las cuentas corrientes.
    private static final String fileName = "current_accounts.json";

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

    /**
     * Implementación de método que crea una nueva cuenta de tipo corriente.
     * Método heredado de IAccountRepository y extendido de AccountJSON.
     *
     * @param client_id : id del cliente al que se le creará la nueva cuenta
     * @param sucursal_id : Sucursal desde donde se crea la cuenta.
     * @param account_id : ID_cuenta o nit de la empresa.
     * @return  Objeto de tipo Account
     * @throws DuplicateKeyException: Lanza la excepción cuando la llave account_id ya está en el sistema.
     * @throws KeyDoesNotExistException: Lanza la excepción cuando la llave client_id no existe.
     */
    @Override
    public CurrentAccount createNewAccount(String client_id, String sucursal_id, String account_id) throws DuplicateKeyException, KeyDoesNotExistException {
        // (1) Verifica que el cliente si exista.
        IClientRepository clientRepository = new CompanyClientJSON();
        System.out.println("este es el nit que está buscando" +account_id);
        try{
            clientRepository.findClient(account_id, "company_clients.json");
        }catch (KeyDoesNotExistException kne){
            throw new KeyDoesNotExistException(account_id);
        }

        // (2) Verifica que esa account_id (NIT) no exista.
        HashMap<String, LinkedTreeMap<String,Object>> accounts= new HashMap<>();
        if(accounts.containsKey( account_id )){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(account_id);
        }
        Date fecha = new Date();

        // (3) Crea una nueva cuenta de CORRIENTE
        CurrentAccount account = new CurrentAccount(account_id, client_id, sucursal_id, false, Float.parseFloat("0"), new ArrayList(), fecha.toString());

        // (4) Sobreescribe el Json con todas las cuentas que aloja el Diccionario llamado account
        add(account, fileName);

        // (5) Asocia una cuenta al cliente.
        clientRepository.associateAccountToClient(account_id, client_id);

        return account;

    }

    /**
     * Implementación de método que activa o cambia el estado de una cuenta corriente.
     * Método extendido de AccountJson.
     *
     * @param account_id : Llave de la cuenta al que se le cambiará el estado.
     * @param is_active : Nuevo estado de la cuenta.
     * @return  : Retorna True si fué posible actualizar el estado de la cuenta.
     * @throws KeyDoesNotExistException: Lanza la excepción cuando la llave (id_account) de la cuenta
     *                                      no es encontrada.
     */
    @Override
    public boolean activeAccount(String account_id, Boolean is_active)throws KeyDoesNotExistException{
        if(is_active){     //Si ingresa es porque la cuenta existe
            return updateStatus(account_id, fileName);
        }
        return false;
    }


}
