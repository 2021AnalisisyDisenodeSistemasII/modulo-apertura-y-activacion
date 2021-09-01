package opActAcct.starBankbackend.repository;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExist;

public interface ISavingAccountRepository {
    void createANewAccount(String client_id, String sucursal_id) throws DuplicateKeyException, KeyDoesNotExist;

}
