package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DtoLogin {
    private String email;
    private String password;
}
