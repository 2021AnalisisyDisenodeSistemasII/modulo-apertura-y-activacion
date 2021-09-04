package opActAcct.starBankbackend.services;
import opActAcct.starBankbackend.repository.repositoryJson.*;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;
import opActAcct.starBankbackend.repository.interfaces.ISavingAccountRepository;
import opActAcct.starBankbackend.services.exception.ObjectDoesNotExistException;
import org.springframework.stereotype.Service;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;

@Service
public class SavingAccountServices {

    private ISavingAccountRepository accountRepository = new SavingAccountJSON();    //Implementación

    public void createANewAccount(String client_id, String sucursal_id)throws ObjectAlreadyExistsException, ObjectDoesNotExistException {
        try{
            accountRepository.createNewAccount(client_id, sucursal_id);
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
}
