package opActAcct.starBankbackend.repository.repositoryJson;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.model.Client;
import opActAcct.starBankbackend.model.CompanyClient;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;
import opActAcct.starBankbackend.repository.interfaces.IClientRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CompanyClientJSON extends ClientJSON implements IClientRepository {

    //Nombre del archivo tipo JSON donde estarán los clientes tipo Empresa.
    private static final String fileName = "company_clients.json";
    //Mapa con los clientes que se lean cada vez del archivo JSON.
    private static HashMap<String, LinkedTreeMap<String,Object>> clients= new HashMap<>();


    /**
     * Implementación de método que agrega la información que se tiene en objectToWrite al archivo llamado como está en la variable fileName.
     * El método devuelve una excepción si la clave primaria del objeto que se quiere agregar ya existe en el archivo.
     *
     * Este método se extiende de la interfaz JsonImplmentation, y también
     * se extiende de la clase abstracta ClientJSON
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
        Client client = (Client) objectToWrite;
        clients= (HashMap) read(fileName);
        if(clients.containsKey( client.getClient_id())){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(client.getClient_id());
        }

        // (2) Construye el Mapa Organizado con los atributos del objeto.
        CompanyClient companyClient = (CompanyClient) client;

        LinkedTreeMap mapAttributes = new LinkedTreeMap();
        mapAttributes.put("accounts",companyClient.getAccounts());
        mapAttributes.put("phone",companyClient.getPhone());
        mapAttributes.put("client_name",companyClient.getClient_name());
        mapAttributes.put("client_occupation",companyClient.getOccupation());
        mapAttributes.put("client_address",companyClient.getClient_address());
        mapAttributes.put("client_id",companyClient.getClient_id());
        mapAttributes.put("company_name",companyClient.getCompany_name());
        mapAttributes.put("cluster",companyClient.getCluster());

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

    /**
     * Implementación de método que actualiza la información de un objeto una llave ya existente.
     *
     * Este método se extiende de la interfaz JsonImplmentation, y tambien
     * se extiende de la clase abstracta ClientJSON
     *
     * @param objectToUpdate : Objeto actualizado.
     * @param fileName : Nombre del Archivo al que se le agregará información.
     * @throws KeyDoesNotExistException: Clave de objeto que no existe en el archivo.
     */
    @Override
    public void update(Object objectToUpdate, String fileName) throws KeyDoesNotExistException {

        // (1) Se comprueba que EXISTA el ID.
        Client client=  (Client) objectToUpdate;
        clients =(HashMap) findClient(client.getClient_id(), fileName);

        String stringToWrite ="";

        // (2) Construye el Mapa Organizado con los atributos del objeto.
        CompanyClient companyClient = (CompanyClient) client;

        LinkedTreeMap mapAttributes = new LinkedTreeMap();
        mapAttributes.put("accounts",companyClient.getAccounts());
        mapAttributes.put("phone",companyClient.getPhone());
        mapAttributes.put("client_name",companyClient.getClient_name());
        mapAttributes.put("client_occupation",companyClient.getOccupation());
        mapAttributes.put("client_address",companyClient.getClient_address());
        mapAttributes.put("client_id",companyClient.getClient_id());
        mapAttributes.put("company_name",companyClient.getCompany_name());
        mapAttributes.put("cluster",companyClient.getCluster());

        clients.put(client.getClient_id(), mapAttributes);


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

    /**
     * Implementación de método que agrega una cuenta (account_id) a un usuario (client_id)
     *
     * Este método hereda de la interfaz IClientRepository
     *
     * @param nit : Llave de la cuenta (account_id o nit) que se alojará en la lista de arreglo de cuentas del usuario.
     * @param client_id : Llave del cliente al que se le va a agregar un Account_id
     * @throws KeyDoesNotExistException: Cuando el cliente no existe, lanza esta excepción.
     */
    @Override
    public void associateAccountToClient(String nit, String client_id) throws KeyDoesNotExistException {
        //Busca a cliente y lo extrae con sus datos
        clients = (HashMap) findClient(client_id, fileName);
        LinkedTreeMap<String,Object> clientMap = clients.get(client_id);

        //Actualiza la lista de cuentas que tiene asociada
        ArrayList clientAccounts = (ArrayList) clientMap.get("accounts");
        clientAccounts.add(nit);

        //Crea el nuevo cliente.
        Client client = new CompanyClient(
                client_id,
                clientAccounts,
                (String) clientMap.get("phone"),
                (String) clientMap.get("client_name"),
                (String) clientMap.get("client_occupation"),
                (String) clientMap.get("client_address"),
                (String) clientMap.get("company_name"),
                (String) clientMap.get("cluster"));

        //Actualiza el cliente en el JSON
        this.update(client, fileName);
    }





}
