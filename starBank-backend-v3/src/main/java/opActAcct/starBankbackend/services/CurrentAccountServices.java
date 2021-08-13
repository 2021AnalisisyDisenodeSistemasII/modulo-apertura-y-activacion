package opActAcct.starBankbackend.services;
import opActAcct.starBankbackend.repository.*;
import opActAcct.starBankbackend.repository.JsonRepository.*;
import org.springframework.stereotype.Service;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;

@Service
public class CurrentAccountServices {

    private ICurrentAccountRepository accountRepository = new CurrentAccountJSON();    //Implementación

    public void createANewAccount(String account_id, String client_id, String sucursal_id)throws ObjectAlreadyExistsException{
        try{
            accountRepository.createANewAccount(account_id, client_id, sucursal_id);
        }
        catch(DuplicateKeyException dke){
            System.out.println(dke);
            throw new ObjectAlreadyExistsException(String.format("La cuenta ya existe" ));
        }

    }


}
