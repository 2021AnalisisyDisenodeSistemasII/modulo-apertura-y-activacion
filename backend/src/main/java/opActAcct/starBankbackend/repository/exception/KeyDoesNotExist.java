package opActAcct.starBankbackend.repository.exception;

public class KeyDoesNotExist extends Exception{
    public KeyDoesNotExist(String message) {
        super("No existe un objeto con esta clave: "+ message);
    }
}
