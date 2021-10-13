package opActAcct.starBankbackend.repository;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

import java.io.*;
import java.util.HashMap;

/**
 * IMPLEMENTACIÓN del programa con archivos .JSON
 */
public abstract class JsonImplementation implements  IImplementation{

    /**
     * Método que agrega la información que se tiene en objectToWrite al archivo llamado como está en la variable fileName.
     * El método devuelve una excepción si la clave primaria del objeto que se quiere agregar ya existe en el archivo.
     *
     * Este método es la implementación de JSON a nuestro Programa.
     *
     * @param objectToWrite : Objeto que se copiará en el archivo.
     * @param fileName : Nombre del Archivo al que se le agregará información.
     * @throws DuplicateKeyException: Lanza la excepción cuando la clave primaria
     * del objeto que se quiere agregar ya existe en el archivo.
     */
    @Override
    public abstract void add(Object objectToWrite, String fileName) throws DuplicateKeyException;

    /**
     * Método que actualiza la información de un objeto una llave ya existente.
     *
     * Este método es la implementación de JSON a nuestro Programa.
     *
     * @param objectToUpdate : Objeto actualizado.
     * @param fileName : Nombre del Archivo al que se le agregará información.
     * @throws KeyDoesNotExistException: Clave de objeto que no existe en el archivo.
     */
    @Override
    public abstract void update(Object objectToUpdate, String fileName) throws KeyDoesNotExistException;

    /**
     * Implementación de método para leer información del archivo.
     *
     * @param fileName : Nombre del archivo que se lee´ra
     * @return Object tipo Map: Diccionario con objetos encontrados en el archivo JSON.
     */
    @Override
    public Object read(String fileName) {


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
            return new HashMap<String, LinkedTreeMap<String,Object>>();
        }
        System.out.println("\n"+"Lectura sobre "+fileName+". Diccionario con los valores:\n" + map.values() + "\n");

        return map;

    }


}
