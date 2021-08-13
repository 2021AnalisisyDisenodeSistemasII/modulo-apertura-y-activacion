package opActAcct.starBankbackend.repository.JsonRepository;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.model.SavingAccount;
import opActAcct.starBankbackend.repository.IMap;
import opActAcct.starBankbackend.repository.ISavingAccountRepository;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;

import java.io.*;
import java.util.*;

public class SavingAccountJSON implements ISavingAccountRepository, IJson, IMap {

    private String fileName = "saving_accounts.json";
    private HashMap<String, LinkedTreeMap<String,Object>> accounts= new HashMap<>();

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
    public String mapToStringInJSONFormat(String key, LinkedTreeMap map) {
        String stringTransactions = "[\n";
        ArrayList arrayAccounts = (ArrayList) map.get("transactions");
        for ( Object s: arrayAccounts) {
            stringTransactions = stringTransactions.concat( "\t\t\""+ s.toString() +"\",\n");
        }
        if(stringTransactions.contains(",")){
            stringTransactions= stringTransactions.substring(0,stringTransactions.lastIndexOf(','));
        }
        stringTransactions = stringTransactions.concat("\n\t]");

        return  "\t\"" + key + "\": {\n" +
                "\t\t\"isActive\": " + map.get("isActive") + ",\n" +
                "\t\t\"client_id\": \"" + map.get("client_id") + "\",\n" +
                "\t\t\"balance\": \"" + map.get("balance") + "\",\n" +
                "\t\t\"sucursal_id\": \"" + map.get("sucursal_id") + "\",\n" +
                "\t\t\"transactions\": " + stringTransactions + ",\n" +
                "\t\t\"creation_date\": \"" + map.get("creation_date") + "\"\n" +
                "\t}";
    }

    @Override
    public LinkedTreeMap objectToMapInJSONFormat(Object object) {
        SavingAccount account = (SavingAccount) object;

        LinkedTreeMap mapAttributes = new LinkedTreeMap();

        mapAttributes.put("isActive",account.isStatus());
        mapAttributes.put("client_id", account.getClient_id());
        mapAttributes.put("balance",account.getBalance());
        mapAttributes.put("sucursal_id", account.getSucursal_id());
        mapAttributes.put("transactions", account.getTransactions());
        mapAttributes.put("creation_date", account.getCreation_date());

        return mapAttributes;
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
    public void writeJson(String string) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(string);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void addJson(Object object) throws DuplicateKeyException {
        /**
         * (1)
         * Se comprueba que el ID no exista.
         */
        SavingAccount account = (SavingAccount) object;
        accounts= (HashMap) readJson();
        if(accounts.containsKey( account.getAccount_id())){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(account.getAccount_id());
        }

        /**
         * (2)
         * Agrega al diccionario de los clientes, el nuevo objeto tipo HashMap con los datos del cliente
         */
        accounts.put(((SavingAccount) object).getAccount_id(), objectToMapInJSONFormat(object));

        /**
         * (3)
         * Se crea el String (de todos los clientes anidados) para poner en el JSON.
         */
        String stringAccountHashMapFormat= "{\n";
        //Se Itera el Diccionario
        for (Map.Entry<String, LinkedTreeMap<String,Object>> accountLoop : accounts.entrySet()) {
            //Cada objeto del diccionario se convierte en string. Esto con el método mapToStringInJSONFormat()
            stringAccountHashMapFormat = stringAccountHashMapFormat.concat(
                    mapToStringInJSONFormat(accountLoop.getKey() ,accountLoop.getValue()) + ",\n");

        }
        //Elimina la última coma (,) del string que contiene todos los clientes
        stringAccountHashMapFormat = stringAccountHashMapFormat.substring(0,
                stringAccountHashMapFormat.lastIndexOf(','));
        //Añade el último (}) para el string que contiene todos los clientes
        stringAccountHashMapFormat = stringAccountHashMapFormat.concat("\n}");

        /**
         * (4)
         * Se copia sobre el archivo el string con todos las cuentas en clase JSON.HashMap
         */
        writeJson(stringAccountHashMapFormat);
    }

    @Override
    public void createANewAccount(String client_id, String sucursal_id) throws DuplicateKeyException {
        String account_id = UUID.randomUUID().toString();
        if(accounts.containsKey( account_id )){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(account_id);
        }
        Date fecha = new Date();
        SavingAccount account = new SavingAccount(account_id, client_id, sucursal_id, false, Float.parseFloat("0"), new ArrayList(), fecha.toString());
        addJson(account);
    }
}
