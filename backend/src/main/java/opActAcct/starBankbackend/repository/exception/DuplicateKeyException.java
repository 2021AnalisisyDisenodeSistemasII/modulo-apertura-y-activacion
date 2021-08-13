package opActAcct.starBankbackend.repository.exception;

public class DuplicateKeyException extends Exception {
    public DuplicateKeyException(String key) {
        super(String.format("Ya existe un registro con esa llave: %s", key));
    }
}
