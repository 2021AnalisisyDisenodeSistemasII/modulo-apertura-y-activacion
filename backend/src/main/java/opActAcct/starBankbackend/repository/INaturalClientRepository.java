package opActAcct.starBankbackend.repository;

import opActAcct.starBankbackend.model.NaturalClient;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.HashMap;

public interface INaturalClientRepository{

    void registerNaturalClient(NaturalClient client) throws DuplicateKeyException;
/*
    NaturalClient stringToNaturalClient(String string);
    String naturalClientToString(NaturalClient client);

    Optional<NaturalClient> consultClientId(String id);
*/

}
