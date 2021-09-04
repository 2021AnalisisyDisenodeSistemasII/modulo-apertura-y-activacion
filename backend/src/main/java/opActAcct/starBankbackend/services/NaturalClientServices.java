package opActAcct.starBankbackend.services;

import opActAcct.starBankbackend.repository.interfaces.IClientRepository;
import opActAcct.starBankbackend.repository.repositoryJson.NaturalClientJSON;
import org.springframework.stereotype.Service;


@Service
public class NaturalClientServices {

    private IClientRepository naturalClient = (IClientRepository) new NaturalClientJSON();    //Implementación del cliente JSON

}
