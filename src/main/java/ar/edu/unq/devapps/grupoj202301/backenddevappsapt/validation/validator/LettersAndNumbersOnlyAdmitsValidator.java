package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.validator;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.LettersAndNumbersOnlyAdmits;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class LettersAndNumbersOnlyAdmitsValidator implements ConstraintValidator<LettersAndNumbersOnlyAdmits, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       return value.matches("^[a-zA-Z0-9ñÑ]+$");
    }
}