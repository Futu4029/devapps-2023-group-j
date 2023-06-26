package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null;
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

    @ExceptionHandler(ElementNotRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleElementNotRegisteredException(ElementNotRegisteredException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleUserException(UserException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations().stream().toList();
        String field = constraintViolations.get(0).getPropertyPath().toString();
        String message = constraintViolations.get(0).getMessageTemplate();
        return ResponseEntity.badRequest().body("Field " + field + " has an error: " + message);
    }
}