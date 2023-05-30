package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String[] parts = fieldError.getField().split("\\.");
        String field = parts[0];
        if(parts.length > 1) {
            field = parts[0] + parts[1].substring(0, 1).toUpperCase() + parts[1].substring(1);
        }
        String message = fieldError.getDefaultMessage();
        return ResponseEntity.badRequest().body("Field " + field + " has an error: " + message);
    }

    @ExceptionHandler(ElementAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleElementAlreadyRegisteredException(ElementAlreadyRegisteredException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}