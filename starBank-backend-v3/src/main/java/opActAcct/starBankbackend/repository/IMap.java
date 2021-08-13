package opActAcct.starBankbackend.repository;
import com.google.gson.internal.LinkedTreeMap;
import java.util.HashMap;

public interface IMap {
    String mapToStringInJSONFormat(String value, LinkedTreeMap map);
    LinkedTreeMap objectToMapInJSONFormat(Object object);
}
