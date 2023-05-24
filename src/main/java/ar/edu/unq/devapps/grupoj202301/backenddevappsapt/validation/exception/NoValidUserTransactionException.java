package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception;

public class NoValidUserTransactionException extends RuntimeException {
    public NoValidUserTransactionException(String message) {
        super(message);
    }
}
