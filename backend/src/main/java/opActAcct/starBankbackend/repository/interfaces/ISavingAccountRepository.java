package opActAcct.starBankbackend.repository.interfaces;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

public interface ISavingAccountRepository {
    void createNewAccount(String client_id, String sucursal_id) throws DuplicateKeyException, KeyDoesNotExistException;

}
