package opActAcct.starBankbackend.repository.repositoryJson;

import com.google.gson.Gson;
import opActAcct.starBankbackend.model.Client;
import opActAcct.starBankbackend.model.CompanyClient;
import opActAcct.starBankbackend.model.NaturalClient;

import java.io.*;
import java.util.*;

import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

public class NaturalClientJSON extends ClientJSON {

    private static String fileName = "natural_clients.json";
    private static HashMap<String, LinkedTreeMap<String,Object>> clients= new HashMap<>();

    @Override
    public void associateAccountToClient(String account_id, String client_id) throws  KeyDoesNotExistException {
        //Busca a cliente y lo extrae con sus datos

        clients =(HashMap) findClient(client_id, fileName);

        LinkedTreeMap clientMap = clients.get(client_id);

        //Actualiza la lista de cuentas que tiene asociada
        ArrayList clientAccounts = (ArrayList) clientMap.get("accounts");
        clientAccounts.add(account_id);

        //Crea el nuevo cliente.
        Client client = new NaturalClient(
                client_id,
                clientAccounts,
                (String) clientMap.get("phone"),
                (String) clientMap.get("client_name"),
                (String) clientMap.get("client_occupation"),
                (String) clientMap.get("client_address"));

        //Actualiza el cliente en el JSON
        updateJson(client, fileName);

    }

    @Override
    public void addToJson(Object objectToWrite, String fileName) throws DuplicateKeyException {
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
        Client client = (Client) objectToWrite;
        clients= (HashMap) readJson(fileName);
        if(clients.containsKey( client.getClient_id())){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(client.getClient_id());
        }

        // (2) Construye el Mapa Organizado con los atributos del objeto.
        NaturalClient naturalClient = (NaturalClient) client;

        LinkedTreeMap mapAttributes = new LinkedTreeMap();
        mapAttributes.put("accounts",naturalClient.getAccounts());
        mapAttributes.put("phone",naturalClient.getPhone());
        mapAttributes.put("client_name",naturalClient.getClient_name());
        mapAttributes.put("client_occupation",naturalClient.getOccupation());
        mapAttributes.put("client_address",naturalClient.getClient_address());

        clients.put(client.getClient_id(), mapAttributes);

        // (3) Con la librería de GSON, Convierte los objetos tipo Map a string de Json
        Gson gson = new Gson();
        String stringToWrite = gson.toJson(clients);
        System.out.println("string a copiar en el JSON de cuentas : \n" + stringToWrite + "\n");

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
    public void updateJson(Object clientObject, String fileName) throws KeyDoesNotExistException {

        // (1) Se comprueba que EXISTA el ID.
        NaturalClient naturalClient = (NaturalClient) clientObject;
        clients =(HashMap) findClient(naturalClient.getClient_id(), fileName);

        String stringToWrite ="";

        // (2) Construye el Mapa Organizado con los atributos del objeto.
        LinkedTreeMap mapAttributes = new LinkedTreeMap();

        mapAttributes.put("accounts",naturalClient.getAccounts());
        mapAttributes.put("phone",naturalClient.getPhone());
        mapAttributes.put("client_name",naturalClient.getClient_name());
        mapAttributes.put("client_occupation",naturalClient.getOccupation());
        mapAttributes.put("client_address",naturalClient.getClient_address());

        clients.put(naturalClient.getClient_id(), mapAttributes);

        // (3) Con la librería de GSON, Convierte los objetos tipo Map a string de Json
        Gson gson = new Gson();
        stringToWrite = gson.toJson(clients);

        //(4) Sobreescribe el archivo con la información de los clientes en formato JSON
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(stringToWrite);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }



}
