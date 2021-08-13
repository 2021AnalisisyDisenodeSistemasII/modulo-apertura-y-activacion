package opActAcct.starBankbackend.repository;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
public interface ICurrentAccountRepository {

    void createANewAccount(String account_id, String client_id, String sucursal_id) throws DuplicateKeyException;
}
