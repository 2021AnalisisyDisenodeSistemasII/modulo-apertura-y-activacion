package opActAcct.starBankbackend.repository.interfaces;

import opActAcct.starBankbackend.model.Account;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

public interface IAccountRepository {
    Account createNewAccount(String client_id,
                             String sucursal_id,
                             String account_id)
            throws DuplicateKeyException,
            KeyDoesNotExistException;

    boolean activeAccount(String account_id, Boolean is_active)
        throws KeyDoesNotExistException;

    boolean updateStatus(String account_id, String fileName)
            throws KeyDoesNotExistException;
}
