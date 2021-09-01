package opActAcct.starBankbackend.repository.JsonRepository;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.model.SavingAccount;
import opActAcct.starBankbackend.repository.IClientRepository;
import opActAcct.starBankbackend.repository.ISavingAccountRepository;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExist;

import java.io.*;
import java.util.*;

public class SavingAccountJSON implements ISavingAccountRepository, IJson {

    private static String fileName = "saving_accounts.json";
    private static HashMap<String, LinkedTreeMap<String,Object>> accounts= new HashMap<>();

    public SavingAccountJSON() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
        }
        catch (FileNotFoundException ex) {    //Excepción de cuando no encuentra un archivo con el nombre alojado en la variable "fileName"
            //Se crea un archivo con el nombre alojado en la variable "fileName"
            File file = new File(fileName);
        }
        catch (IOException ex) {
            System.out.println("Problema, mirar error IOE");
        }
    }

    @Override
    public Object readJson() {

        Gson gson = new Gson();        //Variable para API Gson. Esta nos regalará los metodos para pasar entre JAVA y Json
        String stringJson = "";       //Aquí se concatenará lo que se lee del archivo .json

        //Se lee Línea por línea el archivo .json y se extrae su contenido en la variable stringJson
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = br.readLine()) != null) {
                stringJson+= line;
            }
        } catch (FileNotFoundException ex) {    //Excepción de cuando no encuentra un archivo con el nombre alojado en la variable "fileName"

            //Se crea un archivo con el nombre alojado en la variable "fileName"
            File file = new File(fileName);

            try{                            //Crea el archivo sin problemas
                file.createNewFile();
            }catch(Exception e){            //Hay problemas para crear el archivo.
                System.out.println("Problema, creando el archivo. Excepción: "+ e);
            }
        } catch (IOException ex) {
            System.out.println("Problema, mirar error IOE");
        }

        HashMap map = gson.fromJson(stringJson,HashMap.class);  //Conversión de STRING a un objeto de tipo diccionario o hashMap
        if (map==null){
            return new HashMap<String,LinkedTreeMap<String,Object>>();
        }
        System.out.println("\n"+"Lectura sobre "+fileName+". Diccionario con los valores:\n" + map.values() + "\n");

        return map;

    }

    @Override
    public void writeJson(Object objectToWrite) throws DuplicateKeyException {

        /* (1)
         * Se comprueba que el ID no exista.
         */
        SavingAccount accountToWrite = (SavingAccount) objectToWrite;
        accounts= (HashMap) readJson();
        if(accounts.containsKey( accountToWrite.getAccount_id())){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(accountToWrite.getAccount_id());
        }

        /* (2)
         * Construye el Mapa Organizado con los atributos del objeto.
         */
        LinkedTreeMap mapAttributes = new LinkedTreeMap();
        SavingAccount account = (SavingAccount) objectToWrite;
        mapAttributes.put("isActive", account.isStatus());
        mapAttributes.put("client_id", account.getClient_id());
        mapAttributes.put("balance",account.getBalance());
        mapAttributes.put("sucursal_id", account.getSucursal_id());
        mapAttributes.put("transactions", account.getTransactions());
        mapAttributes.put("creation_date", account.getCreation_date());

        accounts.put(account.getAccount_id(), mapAttributes);

        /* (3)
         *
         */
        Gson gson = new Gson();
        String stringToWrite = gson.toJson(accounts);
        System.out.println("string a copiar en el JSON: \n" + stringToWrite + "\n");


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
     * Entradas: Client_id, sucursal_id - Estas entradas son los parámetros de la cuenta que se creará.
     * Método que construye el objeto Tipo SavingAccount con todos sus atributos.
     * El método hace un llamado a **writeJson()** y lo copia en el archivo.
     *
     * @param client_id
     * @param sucursal_id
     * @throws DuplicateKeyException
     */
    @Override
    public void createANewAccount(String client_id, String sucursal_id) throws DuplicateKeyException, KeyDoesNotExist {
        /* (1)
         * Verifica que el cliente si exista.
         */
        try{
            IClientRepository clientRepository = new ClientJSON();
            Object client = clientRepository.findClient(client_id);
        }catch (KeyDoesNotExist kne){
            throw new KeyDoesNotExist(client_id);
        }

        /* (2)
         * Crea una nueva cuenta de ahorros
          */
        String account_id = UUID.randomUUID().toString();
        if(accounts.containsKey( account_id )){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(account_id);
        }
        Date fecha = new Date();
        SavingAccount account = new SavingAccount(account_id, client_id, sucursal_id, false, Float.parseFloat("0"), new ArrayList(), fecha.toString());

        // Sobreescribe el Json con todas las cuentas que aloja el Diccionario llamado account
        writeJson(account);
    }


}
