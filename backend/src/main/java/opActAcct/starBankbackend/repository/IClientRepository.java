package opActAcct.starBankbackend.repository;

import opActAcct.starBankbackend.model.NaturalClient;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExist;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.HashMap;

public interface IClientRepository {

    void registerNaturalClient(NaturalClient client) throws DuplicateKeyException;
    Object  findClient(String id_client) throws KeyDoesNotExist;

}
