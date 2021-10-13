package opActAcct.starBankbackend.repository.repositoryJson;

import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.model.*;
import opActAcct.starBankbackend.repository.interfaces.IClientRepository;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

import java.util.*;

public class SavingAccountJSON extends AccountJSON  {

    //Nombre de archivo donde se alojarán las cuentas de ahorros.
    private static final String fileName = "saving_accounts.json";

    public SavingAccountJSON() {

    }

    /**
     * Implementación de método que crea una nueva cuenta de tipo ahorros.
     * Método heredado de IAccountRepository y extendido de AccountJSON.
     *
     * @param client_id : id del cliente al que se le creará la nueva cuenta
     * @param sucursal_id : Sucursal desde donde se crea la cuenta.
     * @param nit : parámetro nulo, porque Id_cuenta de ahorros
     *                        lo crea en mismo sistemas de manera aleatoria.
     * @return  Objeto de tipo Account
     * @throws DuplicateKeyException: Lanza la excepción cuando la llave account_id ya está en el sistema.
     * @throws KeyDoesNotExistException: Lanza la excepción cuando la llave client_id no existe.
     */
    @Override
    public Account createNewAccount(String client_id, String sucursal_id, String nit) throws DuplicateKeyException, KeyDoesNotExistException {

        // (1) Verifica que el cliente si exista.
        IClientRepository clientRepository = new NaturalClientJSON();
        try{
            clientRepository.findClient(client_id, "natural_clients.json");
        }catch (KeyDoesNotExistException kne){
            throw new KeyDoesNotExistException(client_id);
        }

        // (2) Verifica que esa account_id no exista.
        HashMap<String, LinkedTreeMap<String,Object>> accounts= new HashMap<>();
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

    /**
     * Implementación de método que activa o cambia el estado de una cuenta de ahorros.
     * Método extendido de AccountJson.
     *
     * @param account_id : Llave de la cuenta al que se le cambiará el estado.
     * @param is_active : Nuevo estado de la cuenta.
     * @return  : Retorna True si fué posible actualizar el estado de la cuenta.
     * @throws KeyDoesNotExistException: Lanza la excepción cuando la llave (id_account) de la cuenta
     *                                      no es encontrada.
     */
    @Override
    public boolean activeAccount(String account_id, Boolean is_active) throws KeyDoesNotExistException {
        if(is_active){     //Si ingresa es porque la cuenta existe
            return updateStatus(account_id, fileName);
        }
        return true;
    }




}
