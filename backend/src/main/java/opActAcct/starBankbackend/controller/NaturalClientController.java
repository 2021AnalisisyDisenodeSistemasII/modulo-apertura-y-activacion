package opActAcct.starBankbackend.controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.Gson.*;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Primitives;
import com.google.gson.stream.JsonReader;
import opActAcct.starBankbackend.model.NaturalClient;
import opActAcct.starBankbackend.services.NaturalClientServices;
import opActAcct.starBankbackend.services.exception.ObjectAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path="/nc")
public class NaturalClientController{

    private final NaturalClientServices naturalClientServices;

    public NaturalClientController(NaturalClientServices naturalClientServices) {
        //metodoPruebas();
        this.naturalClientServices = naturalClientServices;
        /*
        ArrayList array = new ArrayList();
        array.add("111");
        addNaturalClient(new NaturalClient("123", array, "849","vane", "est", "cll"));
*/
    }

    @PostMapping(path="/addc")
    public boolean addNaturalClient(NaturalClient client){
        try{
            return naturalClientServices.addNaturalClient(client);
        }
        //El cliente ya existe, se le debe asociar una nueva cuenta.
        catch(ObjectAlreadyExistsException oae){
            return false;
        }
    }

    public void metodoPruebas(){

        //Client client = new NaturalClient("123",new String[10], "319", "Vane", "Est", "Calle" );
        //addNaturalClient((NaturalClient) client);
        String fileName= "natural-clients.json";

        System.out.println("1. string");

        Gson gson = new Gson();        //Variable para API Gson. Esta nos regalará los metodos para pasar entre JAVA y Json
        String stringJson = "";       //Aquí se concatenará lo que se lee del archivo .json

        System.out.println("2. json");
        //Se lee Línea por línea el archivo .json y se extrae su contenido en la variable stringJson
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = br.readLine()) != null) {
                stringJson+= line;
            }
            System.out.println("3. entró");
        } catch (FileNotFoundException ex) {
            System.out.println("4. falló");
            //System.out.println(ex.getMessage());
            File file = new File(fileName);
            try{
                System.out.println("5. lo creó");
                file.createNewFile();
            }catch(Exception e){
                System.out.println("6. falló la creada");
                //System.out.println(e);
            }
        } catch (IOException ex) {
            System.out.println("7. falló ioe");
            //System.out.println(ex.getMessage());
        }

        HashMap map = gson.fromJson(stringJson,HashMap.class);

        System.out.println("final, final");

    }


/*
    @PostMapping(path = "/post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String postNaturalClients(@RequestBody NaturalClient naturalClient){
        return "hola: " + naturalClient.getClient_id();
    }
    **/
}
