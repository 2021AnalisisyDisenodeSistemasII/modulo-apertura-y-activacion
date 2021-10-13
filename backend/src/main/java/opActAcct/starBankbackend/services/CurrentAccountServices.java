package opActAcct.starBankbackend.services;
import opActAcct.starBankbackend.model.CurrentAccount;
import opActAcct.starBankbackend.repository.interfaces.IAccountRepository;
import opActAcct.starBankbackend.repository.repositoryJson.*;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;
import opActAcct.starBankbackend.services.exception.ObjectDoesNotExistException;
import org.springframework.stereotype.Service;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;

/**
 * Clase con la lógica de negocio
 */
@Service
public class CurrentAccountServices {

    /**
     * Implementación del programa a la base de datos.
     * Esto puede cambiar de acuerdo a la implementación que se quiera hacer.
     *
     * Por ejemplo:
     *
     * Se requiere hacer una implementación para una base de datos llamada BDNueva.
     * La instancia quedaría así:
     * private IAccountRepository accountRepository = new CurrentAccountBDNueva();
     *
     */
    private IAccountRepository accountRepository = new CurrentAccountJSON();    //Implementación JSON

    /**
     * Servicio o método que crea una nueva cuenta.
     * @param client_id :
     * @param sucursal_id :
     * @param nit :
     * @return Objeto creado
     * @throws ObjectAlreadyExistsException: La cuenta con nit ya existe en el sistema.
     * @throws ObjectDoesNotExistException: El cliente_id no existe en el sistema.
     */
    public CurrentAccount createANewAccount(String client_id, String sucursal_id, String nit) throws ObjectAlreadyExistsException, ObjectDoesNotExistException {
        try{
            return (CurrentAccount) accountRepository.createNewAccount(client_id, sucursal_id, nit);
        }
        catch (KeyDoesNotExistException kne){
            System.out.println(kne);
            throw new ObjectDoesNotExistException(String.format("El nit "+ nit+ " no existe" ));
        }
        catch(DuplicateKeyException dke){
            System.out.println(dke);
            throw new ObjectAlreadyExistsException(String.format("La cuenta ya existe" ));
        }

    }

    /**
     * Servicio o método para activar una cuenta.
     * @param account_id :
     * @param is_active :
     * @return True, si la cuenta se pudo activar.
     * @throws ObjectDoesNotExistException: La cuenta account_id no existe en el sistema.
     */
    public boolean ActiveCurrentAccount (String account_id, Boolean is_active)throws ObjectDoesNotExistException{
        try{
            return accountRepository.activeAccount(account_id, is_active);
        }
        catch (KeyDoesNotExistException kne){
            System.out.println(kne);
            throw new ObjectDoesNotExistException(String.format("El cliente no existe" ));
        }
    }


}
