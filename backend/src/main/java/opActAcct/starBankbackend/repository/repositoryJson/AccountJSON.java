package opActAcct.starBankbackend.repository.repositoryJson;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.model.Account;
import opActAcct.starBankbackend.repository.JsonImplementation;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;
import opActAcct.starBankbackend.repository.interfaces.IAccountRepository;

import java.io.*;
import java.util.HashMap;

public abstract class AccountJSON extends JsonImplementation implements IAccountRepository {

    private static HashMap<String, LinkedTreeMap<String,Object>> accounts= new HashMap<>();

    /**
     * Implementación del método que agrega la información que se tiene
     * en objectToWrite al archivo llamado como está en la variable fileName.
     * El método devuelve una excepción si la clave primaria del objeto que
     * se quiere agregar ya existe en el archivo.
     *
     * Este método se extiende de la interfaz JsonImplementation
     *
     * @param objectToWrite : Objeto que se copiará en el archivo.
     * @param fileName : Nombre del Archivo al que se le agregará información.
     * @throws DuplicateKeyException: Lanza la excepción cuando la clave primaria
     * del objeto que se quiere agregar ya existe en el archivo.
     */
    @Override
    public void add(Object objectToWrite, String fileName) throws DuplicateKeyException {

        // (0) Crea el archivo en caso de que no exista
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
        }
        catch (FileNotFoundException ex) {    //Excepción de cuando no encuentra un archivo con el nombre alojado en la variable "fileName"
            //Se crea un archivo con el nombre alojado en la variable "fileName"
            File file = new File(fileName);
            try{                            //Crea el archivo sin problemas
                file.createNewFile();
            }catch(Exception e){            //Hay problemas para crear el archivo.
                System.out.println("Problema, creando el archivo. Excepción: "+ e);
            }
        }
        catch (IOException ex) {
            System.out.println("Problema, mirar error IOE");
        }

        // (1) Se comprueba que el ID no exista.
        Account accountToWrite = (Account) objectToWrite;
        accounts= (HashMap) read(fileName);
        if(accounts.containsKey( accountToWrite.getAccount_id())){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(accountToWrite.getAccount_id());
        }

        // (2) Construye el Mapa Organizado con los atributos del objeto.
        LinkedTreeMap mapAttributes = new LinkedTreeMap();
        mapAttributes.put("isActive", accountToWrite.isStatus());
        mapAttributes.put("client_id", accountToWrite.getClient_id());
        mapAttributes.put("balance",accountToWrite.getBalance());
        mapAttributes.put("sucursal_id", accountToWrite.getSucursal_id());
        mapAttributes.put("transactions", accountToWrite.getTransactions());
        mapAttributes.put("creation_date", accountToWrite.getCreation_date());

        accounts.put(accountToWrite.getAccount_id(), mapAttributes);

        // (3) Con la librería de GSON, Convierte los objetos tipo Map a string de Json
        Gson gson = new Gson();
        String stringToWrite = gson.toJson(accounts);
        //System.out.println("string a copiar en el JSON de cuentas : \n" + stringToWrite + "\n");

        //copiar en el archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(stringToWrite);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Implementación de método que actualiza la información de un objeto una llave ya existente.
     *
     * Este método se extiende de la interfaz JsonImplementation
     *
     * @param objectToWrite : Objeto actualizado.
     * @param fileName : Nombre del Archivo al que se le agregará información.
     * @throws KeyDoesNotExistException: Clave de objeto que no existe en el archivo.
     */
    @Override
    public void update(Object objectToWrite, String fileName) throws KeyDoesNotExistException {

        // (0) Crea el archivo en caso de que no exista
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
        }
        catch (FileNotFoundException ex) {    //Excepción de cuando no encuentra un archivo con el nombre alojado en la variable "fileName"
            //Se crea un archivo con el nombre alojado en la variable "fileName"
            File file = new File(fileName);
            try{                            //Crea el archivo sin problemas
                file.createNewFile();
            }catch(Exception e){            //Hay problemas para crear el archivo.
                System.out.println("Problema, creando el archivo. Excepción: "+ e);
            }
        }
        catch (IOException ex) {
            System.out.println("Problema, mirar error IOE");
        }

        // (1) Se comprueba que EXISTA el ID.
        Account account = (Account) objectToWrite;
        accounts= (HashMap) read(fileName);
        if(!accounts.containsKey( account.getAccount_id())){     //Si ingresa es porque el id NO EXISTE
            throw new KeyDoesNotExistException(account.getAccount_id());
        }

        // (2) Construye el Mapa Organizado con los atributos del objeto.
        LinkedTreeMap mapAttributes = new LinkedTreeMap();

        mapAttributes.put("isActive",account.isStatus());
        mapAttributes.put("client_id",account.getClient_id());
        mapAttributes.put("balance",account.getBalance());
        mapAttributes.put("sucursal_id",account.getSucursal_id());
        mapAttributes.put("transactions",account.getTransactions());
        mapAttributes.put("creation_date",account.getCreation_date());

        accounts.put(account.getAccount_id(), mapAttributes);

        // (3) Con la librería de GSON, Convierte los objetos tipo Map a string de Json
        Gson gson = new Gson();
        String stringToWrite = gson.toJson(accounts);
        System.out.println("Se copiará en el Json de cuentas : \n" + stringToWrite + "\n");

        //(4) Sobreescribe el archivo con la información de los clientes en formato JSON
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(stringToWrite);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Implementación de método que actualiza el estado de una cuenta.
     * No importa el tipo de cuenta.
     *
     * @param account_id : Llave o id de la cuenta que se le quiere cambiar el estado.
     * @param fileName : Nombre del archivo donde se debe actualizar los datos.
     * @return True en caso de que el cambio haya sido exitoso.
     * @throws KeyDoesNotExistException: Cuando la llave de la cuenta no existe en el archivo.
     */
    @Override
    public boolean updateStatus (String account_id, String fileName) throws KeyDoesNotExistException{
        accounts= (HashMap) read(fileName);
        if(accounts.containsKey( account_id )){     //Si ingresa es porque la cuenta existe
            LinkedTreeMap<String, Object> accountInfo = accounts.get(account_id);
            accountInfo.replace("isActive", "true");

            Gson gson = new Gson();
            String stringToWrite = gson.toJson(accounts);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                bw.write(stringToWrite);
                return true;
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }

    /**
     * Método abstracto que crea una nueva cuenta.
     * Método heredado de IAccountRepository e implementado en cada uno de los tipos de cuentas.
     *
     * @param client_id : id del cliente al que se le creará la nueva cuenta
     * @param sucursal_id : Sucursal desde donde se crea la cuenta.
     * @param nit : De acuerdo al tipo de cuenta, este argumento es válido o nulo.
     * @return  Objeto de tipo Account
     * @throws DuplicateKeyException: Lanza la excepción cuando la llave account_id ya está en el sistema.
     * @throws KeyDoesNotExistException: Lanza la excepción cuando la llave client_id no existe.
     */
    @Override
    public abstract Account createNewAccount(
            String client_id,
            String sucursal_id,
            String nit)
            throws DuplicateKeyException,
            KeyDoesNotExistException;

    /**
     * Método abstracto que activa o cambia el estado de una cuenta.
     * Método heredado de IAccountRepository e implementado en cada uno de los tipos de cuentas.
     *
     * @param account_id : Llave de la cuenta al que se le cambiará el estado.
     * @param is_active : Nuevo estado de la cuenta.
     * @return  : Retorna True si fué posible actualizar el estado de la cuenta.
     * @throws KeyDoesNotExistException: Lanza la excepción cuando la llave (id_account) de la cuenta
     *                                      no es encontrada.
     */
    public abstract boolean activeAccount(
            String account_id,
            Boolean is_active)
            throws KeyDoesNotExistException;

}
