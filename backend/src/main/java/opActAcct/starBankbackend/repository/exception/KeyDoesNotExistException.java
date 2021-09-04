package opActAcct.starBankbackend.repository.exception;

public class KeyDoesNotExistException extends Exception{
    public KeyDoesNotExistException(String message) {
        super("No existe un objeto con esta clave: "+ message);
    }
}
