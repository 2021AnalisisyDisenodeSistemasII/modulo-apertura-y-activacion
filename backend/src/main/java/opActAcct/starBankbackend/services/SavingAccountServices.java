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

@Service
public class SavingAccountServices {

    private IAccountRepository accountRepository =  new SavingAccountJSON();    //Implementación

    public SavingAccount createANewAccount(String client_id, String sucursal_id, String acc)throws ObjectAlreadyExistsException, ObjectDoesNotExistException {
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
