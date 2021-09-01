package opActAcct.starBankbackend.repository.JsonRepository;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.model.CurrentAccount;
import opActAcct.starBankbackend.model.SavingAccount;
import opActAcct.starBankbackend.repository.ICurrentAccountRepository;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import java.io.*;
import java.util.*;


public class CurrentAccountJSON implements ICurrentAccountRepository, IJson{

    private static String fileName = "current_accounts.json";
    private static HashMap<String, LinkedTreeMap<String,Object>> accounts= new HashMap<>();

    public CurrentAccountJSON() {
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
/*
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
        CurrentAccount account = (CurrentAccount) object;

        LinkedTreeMap mapAttributes = new LinkedTreeMap();

        mapAttributes.put("isActive",account.isStatus());
        mapAttributes.put("client_id", account.getClient_id());
        mapAttributes.put("balance",account.getBalance());
        mapAttributes.put("sucursal_id", account.getSucursal_id());
        mapAttributes.put("transactions", account.getTransactions());
        mapAttributes.put("creation_date", account.getCreation_date());

        return mapAttributes;
    }
    */

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
        CurrentAccount accountToWrite = (CurrentAccount) objectToWrite;
        accounts= (HashMap) readJson();
        if(accounts.containsKey( accountToWrite.getAccount_id())){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(accountToWrite.getAccount_id());
        }

        /* (2)
         * Construye el Mapa Organizado con los atributos del objeto.
         */
        LinkedTreeMap mapAttributes = new LinkedTreeMap();
        mapAttributes.put("isActive", accountToWrite.isStatus());
        mapAttributes.put("client_id", accountToWrite.getClient_id());
        mapAttributes.put("balance",accountToWrite.getBalance());
        mapAttributes.put("sucursal_id", accountToWrite.getSucursal_id());
        mapAttributes.put("transactions", accountToWrite.getTransactions());
        mapAttributes.put("creation_date", accountToWrite.getCreation_date());

        accounts.put(accountToWrite.getAccount_id(), mapAttributes);

        /* (3)
         *
         */
        Gson gson = new Gson();
        String stringToWrite = gson.toJson(accounts);


        //copiar en el archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(stringToWrite);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createANewAccount(String account_id,String client_id, String sucursal_id) throws DuplicateKeyException {
        if(accounts.containsKey( account_id )){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(account_id);
        }
        Date fecha = new Date();
        CurrentAccount account = new CurrentAccount(account_id, client_id, sucursal_id, false, Float.parseFloat("0"), new ArrayList(), fecha.toString());
        writeJson(account);
    }
}
