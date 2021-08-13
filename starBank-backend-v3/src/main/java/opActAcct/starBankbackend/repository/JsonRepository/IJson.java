package opActAcct.starBankbackend.repository.JsonRepository;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;

import java.util.HashMap;

public interface IJson {

    Object readJson();
    void writeJson(String string);  //HashMap<String, HashMap<String, Object>> map
    void addJson(Object object) throws DuplicateKeyException;    //Añadir al Json un nuevo objeto
/*
    Object jsonToObject(String string);
    String objectToJson(Object object);
*/
}
