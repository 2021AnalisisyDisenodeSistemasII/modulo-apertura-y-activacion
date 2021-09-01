package opActAcct.starBankbackend.repository.JsonRepository;

import com.google.gson.Gson;
import opActAcct.starBankbackend.model.NaturalClient;
import opActAcct.starBankbackend.repository.IClientRepository;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;

import java.io.*;
import java.util.*;

import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExist;

public class ClientJSON implements IClientRepository, IJson{

    private String fileName = "natural-clients.json";
    private HashMap<String, LinkedTreeMap<String, Object>> clients = new HashMap<>();


    @Override
    public void registerNaturalClient(NaturalClient client) throws DuplicateKeyException {

        clients= (HashMap) this.readJson();     //Se actualiza el diccionario con lo que lea en el archivo JSON

        if(clients.containsKey(client.getClient_id())){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(client.getClient_id());
        }
        writeJson(client);                                             //Añade al Json el nuevo Cliente
    }

    @Override
    public Object findClient(String id_client) throws KeyDoesNotExist {
        clients = (HashMap<String, LinkedTreeMap<String, Object>>) readJson();
        if (!clients.containsKey(id_client)){
            throw new KeyDoesNotExist(id_client);
        }else {
            return clients.get(id_client);
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

        System.out.println("\n"+"Lectura sobre "+fileName+". Diccionario con los valores:\n" + map.values() + "\n");

        return map;
    }

    @Override
    public void writeJson(Object object) {  //HashMap<String, String> map

    }

    public LinkedTreeMap objectToMapInJSONFormat(Object object) {

        NaturalClient client = (NaturalClient) object;

        LinkedTreeMap mapAttributes = new LinkedTreeMap();

        mapAttributes.put("accounts",client.getAccounts());
        mapAttributes.put("phone",client.getPhone());
        mapAttributes.put("client_name",client.getClient_name());
        mapAttributes.put("client_occupation",client.getOccupation());
        mapAttributes.put("client_address",client.getClient_address());


        return mapAttributes;
    }


}
