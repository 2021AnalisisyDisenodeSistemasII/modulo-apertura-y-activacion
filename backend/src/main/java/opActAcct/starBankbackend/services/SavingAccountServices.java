package opActAcct.starBankbackend.services;
import opActAcct.starBankbackend.model.Account;
import opActAcct.starBankbackend.model.SavingAccount;
import opActAcct.starBankbackend.repository.interfaces.IAccountRepository;
import opActAcct.starBankbackend.repository.repositoryJson.*;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;
import opActAcct.starBankbackend.services.exception.ObjectDoesNotExistException;
import org.springframework.stereotype.Service;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;

/**
 * Clase con la lógica del negocio
 */
@Service
public class SavingAccountServices {

    /**
     * Implementación del programa a la base de datos.
     * Esto puede cambiar de acuerdo a la implementación que se quiera hacer.
     *
     * Por ejemplo:
     *
     * Se requiere hacer una implementación para una base de datos llamada BDNueva.
     * La instancia quedaría así:
     * private IAccountRepository accountRepository = new SavingAccountBDNueva();
     *
     */
    private IAccountRepository accountRepository =  new SavingAccountJSON();    //Implementación

    /**
     * Servicio o método que crea una nueva cuenta.
     * @param client_id :
     * @param sucursal_id :
     * @param account_id_null : El mismo sistema elige la account_id
     * @return Objeto creado
     * @throws ObjectAlreadyExistsException: La cuenta con account_id ya existe en el sistema.
     * @throws ObjectDoesNotExistException: El cliente_id no existe en el sistema.
     */
    public SavingAccount createANewAccount(String client_id, String sucursal_id, String account_id_null)throws ObjectAlreadyExistsException, ObjectDoesNotExistException {
        try{
            return (SavingAccount) accountRepository.createNewAccount(client_id, sucursal_id, null);
        }
        catch (KeyDoesNotExistException kne){
            System.out.println(kne);
            throw new ObjectDoesNotExistException(String.format("El cliente no existe" ));
        }
        catch(DuplicateKeyException dke){
            System.out.println(dke);
            throw new ObjectAlreadyExistsException(String.format("La cuenta ya existe" ));
        }


    }

    /**
     * Servicio o método para activar una cuenta.
     * @param account_id:
     * @param is_active:
     * @return True: si la cuenta se puedo activar
     * @throws ObjectDoesNotExistException: Cuando el account_id no existe en el sistema.
     */
    public boolean ActiveSavingAccount (String account_id, Boolean is_active)throws ObjectDoesNotExistException{
        try{
            return accountRepository.activeAccount(account_id, is_active);
        }
        catch (KeyDoesNotExistException kne){
            System.out.println(kne);
            throw new ObjectDoesNotExistException(String.format("El cliente no existe" ));
        }
    }
}
