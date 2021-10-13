package opActAcct.starBankbackend.repository.interfaces;

import opActAcct.starBankbackend.model.Account;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

/**
 * Esta interfaz tiene que ser heredada por cada una de las implementaciones
 * de bases de datos que se quieran tener en el programa.
 */
public interface IAccountRepository {

    /**
     * Método que crea una nueva cuenta.
     * Este método es heredado por AccountJSON e implementado en cada uno de los tipos de cuentas.
     *
     * @param client_id : id del cliente al que se le creará la nueva cuenta
     * @param sucursal_id : Sucursal desde donde se crea la cuenta.
     * @param account_id : De acuerdo al tipo de cuenta, este argumento es válido o nulo.
     * @return  Objeto de tipo Account
     * @throws DuplicateKeyException: Lanza la excepción cuando la llave account_id ya está en el sistema.
     * @throws KeyDoesNotExistException: Lanza la excepción cuando la llave client_id no existe.
     */
    Account createNewAccount(String client_id,
                             String sucursal_id,
                             String account_id)
            throws DuplicateKeyException,
            KeyDoesNotExistException;

    /**
     * Método que activa o cambia el estado de una cuenta.
     * Este método es heredado por AccountJSON e implementado en cada uno de los tipos de cuentas.
     *
     * @param account_id : Llave de la cuenta al que se le cambiará el estado.
     * @param is_active : Nuevo estado de la cuenta.
     * @return  : Retorna True si fué posible actualizar el estado de la cuenta.
     * @throws KeyDoesNotExistException: Lanza la excepción cuando la llave (id_account) de la cuenta
     *                                      no es encontrada.
     */
    boolean activeAccount(String account_id, Boolean is_active)
        throws KeyDoesNotExistException;

    /**
     * Método que actualiza el estado de una cuenta.
     * Este método es heredado por la clase abstracta AccountJSON
     * e implementado allí mismo.
     *
     * @param account_id : Llave o id de la cuenta que se le quiere cambiar el estado.
     * @param fileName : Nombre del archivo donde se debe actualizar los datos.
     * @return True en caso de que el cambio haya sido exitoso.
     * @throws KeyDoesNotExistException: Cuando la llave de la cuenta no existe en el archivo.
     */
    boolean updateStatus(String account_id, String fileName)
            throws KeyDoesNotExistException;
}
