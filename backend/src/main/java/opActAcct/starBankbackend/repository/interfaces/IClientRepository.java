package opActAcct.starBankbackend.repository.interfaces;

import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

public interface IClientRepository {

    /**
     * Entradas -> id_cliente :
     * Salida   -> Objeto (en este caso tipo LinkedTreeMap<String,Object>)
     * con los datos del objeto que se creó adentro.
     *
     *
     *
     * @param id_client: Id del cliente que va a buscar.
     * @return LinkedTreeMap<String,Object>
     * @throws KeyDoesNotExistException: Cuando no encuentra un objeto con el id de entrada.
     */
    Object findClient(String id_client, String fileName) throws KeyDoesNotExistException;
    void associateAccountToClient(String account_id, String client_id) throws KeyDoesNotExistException;

}
