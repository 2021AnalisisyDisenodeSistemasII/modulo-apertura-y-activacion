package opActAcct.starBankbackend.services;
import opActAcct.starBankbackend.model.CurrentAccount;
import opActAcct.starBankbackend.repository.interfaces.IAccountRepository;
import opActAcct.starBankbackend.repository.repositoryJson.*;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;
import opActAcct.starBankbackend.services.exception.ObjectDoesNotExistException;
import org.springframework.stereotype.Service;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;

@Service
public class CurrentAccountServices {

    private IAccountRepository accountRepository = new CurrentAccountJSON();    //Implementación

    public CurrentAccount createANewAccount(String account_id, String client_id, String sucursal_id) throws ObjectAlreadyExistsException, ObjectDoesNotExistException {
        try{
            return (CurrentAccount) accountRepository.createNewAccount(client_id, sucursal_id, account_id);
        }
        catch (KeyDoesNotExistException kne){
            System.out.println(kne);
            throw new ObjectDoesNotExistException(String.format("El nit "+ account_id+ " no existe" ));
        }
        catch(DuplicateKeyException dke){
            System.out.println(dke);
            throw new ObjectAlreadyExistsException(String.format("La cuenta ya existe" ));
        }

    }


}
