package opActAcct.starBankbackend.repository;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;


public interface IImplementation {
    void add(Object objectToWrite, String fileName) throws DuplicateKeyException;
    void update(Object object, String fileName) throws KeyDoesNotExistException;
    Object read(String fileName) ;
}
