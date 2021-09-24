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

    @Override
    public abstract void createNewAccount(
            String client_id,
            String sucursal_id,
            String account_id)
            throws DuplicateKeyException,
            KeyDoesNotExistException;

}
