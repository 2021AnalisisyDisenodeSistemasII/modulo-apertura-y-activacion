package opActAcct.starBankbackend.repository;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;

public interface ISavingAccountRepository {
    void createANewAccount(String client_id, String sucursal_id) throws DuplicateKeyException;

}
