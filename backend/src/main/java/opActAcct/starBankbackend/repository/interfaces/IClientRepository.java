package opActAcct.starBankbackend.repository.interfaces;

import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

/**
 * Esta interfaz tiene que ser heredada por cada una de las implementaciones
 * de bases de datos que se quieran tener en el programa.
 */
public interface IClientRepository {

    /**
     * Declaración de método que busca la llave de objeto (cliente) en un archivo.
     *
     * @param id_client: Id del cliente que va a buscar.
     * @return LinkedTreeMap<String,Object> : Cuando la llave (id_cliente) es encontrada, devuelve el objeto con todos sus parámetros en un mapa.
     * @throws KeyDoesNotExistException: Cuando no encuentra un objeto con la llave id_client en el archivo.
     */
    Object findClient(String id_client, String fileName) throws KeyDoesNotExistException;

    /**
     * Declaración de método que agrega una cuenta (account_id) a un usuario (client_id)
     *
     * @param account_id : Llave de la cuenta que se alojará en la lista de arreglo de cuentas del usuario.
     * @param client_id : Llave del cliente al que se le va a agregar un Account_id
     * @throws KeyDoesNotExistException: Cuando el cliente no existe, lanza esta excepción.
     */
    void associateAccountToClient(String account_id, String client_id) throws KeyDoesNotExistException;

}
