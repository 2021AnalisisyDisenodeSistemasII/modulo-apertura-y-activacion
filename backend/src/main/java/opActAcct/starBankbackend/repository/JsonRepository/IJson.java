package opActAcct.starBankbackend.repository.JsonRepository;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;

import java.util.HashMap;

public interface IJson {
    Object readJson();
    void writeJson(Object objectToWrite) throws DuplicateKeyException;
}
