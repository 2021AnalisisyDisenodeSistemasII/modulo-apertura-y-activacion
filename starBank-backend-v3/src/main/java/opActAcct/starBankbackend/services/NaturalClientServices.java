package opActAcct.starBankbackend.services;

import opActAcct.starBankbackend.model.NaturalClient;
import opActAcct.starBankbackend.repository.INaturalClientRepository;
import opActAcct.starBankbackend.repository.JsonRepository.NaturalClientJSON;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NaturalClientServices {

    private INaturalClientRepository naturalClient= new NaturalClientJSON();    //Implementación del cliente JSON

    /**
     * @param client
     * @return
     * @throws ObjectAlreadyExistsException
     */
    public boolean addNaturalClient(NaturalClient client) throws ObjectAlreadyExistsException {
        try{
            this.naturalClient.registerNaturalClient(client);
        }
        catch(DuplicateKeyException dke){
            System.out.println(dke);
            throw new ObjectAlreadyExistsException(String.format("El cliente con id: %s , ya existe.", client.getClient_id() ));
        }
        return true;
    }
}
