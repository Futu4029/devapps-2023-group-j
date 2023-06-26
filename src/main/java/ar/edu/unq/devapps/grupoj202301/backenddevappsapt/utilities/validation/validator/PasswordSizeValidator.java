package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.validator;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.anotation.PasswordSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordSizeValidator implements ConstraintValidator<PasswordSize, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        int passwordLength = value.length();
        return (passwordLength >= 6 && passwordLength <= 32) || passwordLength == 60;
    }
}