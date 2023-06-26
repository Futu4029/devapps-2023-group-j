package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.anotation.PasswordSize;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.anotation.SpecialCharactersOnlyAdmits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class DtoLogin {

    @Email(message = "Please provide a valid email address")
    @NotBlank
    private String email;

    @SpecialCharactersOnlyAdmits
    @PasswordSize
    private String password;
}
