package opActAcct.starBankbackend.repository.repositoryJson;

import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.repository.JsonImplementation;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;
import opActAcct.starBankbackend.repository.interfaces.IClientRepository;

import java.util.HashMap;

public abstract class ClientJSON extends JsonImplementation implements IClientRepository {

    /**
     * Método (no implementado) que agrega la información que se tiene en objectToWrite al archivo llamado como está en la variable fileName.
     * El método devuelve una excepción si la clave primaria del objeto que se quiere agregar ya existe en el archivo.
     *
     * Este método se extiende de la interfaz JsonImplementation
     *
     * @param objectToWrite : Objeto que se copiará en el archivo.
     * @param fileName : Nombre del Archivo al que se le agregará información.
     * @throws DuplicateKeyException: Lanza la excepción cuando la clave primaria
     * del objeto que se quiere agregar ya existe en el archivo.
     */
    @Override
    public abstract void add(Object objectToWrite, String fileName) throws DuplicateKeyException;

    /**
     * Método que actualiza la información de un objeto una llave ya existente.
     *
     * Este método se extiende de la interfaz JsonImplementation
     *
     * @param objectToUpdate : Objeto actualizado.
     * @param fileName : Nombre del Archivo al que se le agregará información.
     * @throws KeyDoesNotExistException: Clave de objeto que no existe en el archivo.
     */
    @Override
    public abstract void update(Object objectToUpdate, String fileName) throws KeyDoesNotExistException;

    /**
     * Método que asocia a un cliente, un número de cuenta.
     * Esto se hace agregando a un arreglo de cliente, el número de cuenta que se asociará.
     * @param account_id : Id o número de cuenta que se agregará al client_id.
     * @param client_id : Id del cliente al que se le agregará una cuenta.
     * @throws KeyDoesNotExistException: Laza excepción cuando la clave del cliente no existe.
     */
    @Override
    public abstract void associateAccountToClient(String account_id, String client_id) throws  KeyDoesNotExistException;

    /**
     * Método que busca un cliente dentro de un archivo.
     *
     * Este método hereda de la interfaz IClientRepository
     *
     * @param id_client : Id del cliente que va a buscar.
     * @param fileName : Nombre del archivo donde se buscará la llave.
     * @return Objeto de tipo Client: Devuelve el cliente cuando encuentra la llave del cliente solicitada.
     * @throws KeyDoesNotExistException: Sucede cuando la llave del cliente no existe en el archivo.
     */
    @Override
    public Object findClient(String id_client, String fileName) throws KeyDoesNotExistException {
        HashMap<String, LinkedTreeMap<String,Object>> clients = (HashMap) read(fileName);
        if (clients.containsKey(id_client)){
            return clients;
        }
        throw new KeyDoesNotExistException(id_client);
    }

}
