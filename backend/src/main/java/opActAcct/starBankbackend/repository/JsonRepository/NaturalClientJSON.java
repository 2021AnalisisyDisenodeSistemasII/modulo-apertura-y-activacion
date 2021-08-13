package opActAcct.starBankbackend.repository.JsonRepository;

import com.google.gson.Gson;
import opActAcct.starBankbackend.model.NaturalClient;
import opActAcct.starBankbackend.repository.IMap;
import opActAcct.starBankbackend.repository.INaturalClientRepository;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import com.google.gson.internal.LinkedTreeMap;

public class NaturalClientJSON implements INaturalClientRepository, IJson, IMap {

    private String fileName= "natural-clients.json";
    private HashMap<String, LinkedTreeMap<String,Object>> clients= new HashMap<>();

    @Override
    public void registerNaturalClient(NaturalClient client) throws DuplicateKeyException {

        clients= (HashMap) this.readJson();     //Se actualiza el diccionario con lo que lea en el archivo JSON

        if(clients.containsKey(client.getClient_id())){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(client.getClient_id());
        }
        addJson(client);                                             //Añade al Json el nuevo Cliente
    }

    /**
     * Variables externas: Utiliza la variable global **fileName**
     *
     * Este método lee un archivo (fileName), convierte los objetos tipo Json a HashMap (Diccionario).
     * Retorna un HashMap (Diccionario) con la información de los clientes naturales.
     *
     * El HashMap tiene la información de la siguiente manera:
     * Clave(key): client_id
     * Valor(value): accounts[], phone, client_name, client_occupation, client_address
     *
     * @return HashMap
     */
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

    /**
     *
     *
     * @param object
     */
    @Override
    public void writeJson(String stringJson) {  //HashMap<String, String> map

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(stringJson);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * ENTRADAS: Método que recibe un objeto, en este caso de tipo HashMap<String,HashMap>
     * donde la CLAVE es un String con el idClient,
     * y el VALOR es un objeto HashMap<string, Object> con los atributos del Cliente.
     *
     * El método lo que hace es copiar en el Json el nuevo diccionario que se genere
     * al agregar el nuevo HashMap con los datos del cliente.
     *
     * Return: Este método no retorna nada, pero si sobreescribe un archivo .JSON
     *
     * @param object
     * @throws DuplicateKeyException
     */
    @Override
    public void addJson(Object object) throws DuplicateKeyException {
        /**
         * (1)
         * Se comprueba que el ID no exista.
         */
        NaturalClient client = (NaturalClient) object;
        clients = (HashMap) readJson();
        if(clients.containsKey( client.getClient_id())){     //Si ingresa es porque el id ya existe
            throw new DuplicateKeyException(client.getClient_id());
        }

        /**
         * (2)
         * Agrega al diccionario de los clientes, el nuevo objeto tipo HashMap con los datos del cliente
         */
        clients.put(((NaturalClient) object).getClient_id(), objectToMapInJSONFormat(object));

        /**
         * (3)
         * Se crea el String (de todos los clientes anidados) para poner en el JSON.
         */
        String stringClientsHashMapFormat= "{\n";
        //Se Itera el Diccionario
        for (Map.Entry<String, LinkedTreeMap<String,Object>> clientLoop : clients.entrySet()) {
            //Cada objeto del diccionario se convierte en string. Esto con el método mapToStringInJSONFormat()
            stringClientsHashMapFormat = stringClientsHashMapFormat.concat(
                            mapToStringInJSONFormat(clientLoop.getKey() ,clientLoop.getValue()) + ",\n");

        }
        //Elimina la última coma (,) del string que contiene todos los clientes
        stringClientsHashMapFormat = stringClientsHashMapFormat.substring(0,
                stringClientsHashMapFormat.lastIndexOf(','));
        //Añade el último (}) para el string que contiene todos los clientes
        stringClientsHashMapFormat = stringClientsHashMapFormat.concat("\n}");

        /**
         * (4)
         * Se copia sobre el archivo el string con todos los clientes en clase JSON.HashMap
         */
        writeJson(stringClientsHashMapFormat);
    }

    /**
     * Recibe un objeto
     * @param map
     * @return
     */
    @Override
    public String mapToStringInJSONFormat(String key, LinkedTreeMap map) {

        String stringAccounts = "[\n";
        ArrayList arrayAccounts = (ArrayList) map.get("accounts");
        for ( Object s: arrayAccounts) {
            stringAccounts = stringAccounts.concat( "            \""+ s.toString() +"\",\n");
        }
        if(stringAccounts.contains(",")){
            stringAccounts= stringAccounts.substring(0,stringAccounts.lastIndexOf(','));
        }
        stringAccounts = stringAccounts.concat("\n\t]");

        return  "    \"" + key + "\": {\n" +
                "\t\"accounts\": " + stringAccounts + ",\n" +
                "\t\"phone\": \"" + map.get("phone") + "\",\n" +
                "\t\"client_name\": \"" + map.get("client_name") + "\",\n" +
                "\t\"client_occupation\": \"" + map.get("client_occupation") + "\",\n" +
                "\t\"client_address\": \"" + map.get("client_address") + "\"\n" +
                "    }";
    }

    /**
     *
     * Método que convierte un objeto tipo Client en un Map, en este caso LinkedTreeMap
     * @param object
     * @return
     */
    @Override
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
