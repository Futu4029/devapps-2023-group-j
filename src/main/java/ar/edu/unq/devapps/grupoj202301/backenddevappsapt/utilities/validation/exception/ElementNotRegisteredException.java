package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ElementNotRegisteredException extends RuntimeException {
    private String message;

    @JsonCreator
    public ElementNotRegisteredException(@JsonProperty("message") String message) {
        super(message);
        this.message = message;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
