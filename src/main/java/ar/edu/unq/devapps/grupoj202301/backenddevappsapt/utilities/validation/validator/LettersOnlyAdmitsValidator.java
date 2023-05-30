package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.validator;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.anotation.LettersOnlyAdmits;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class LettersOnlyAdmitsValidator implements ConstraintValidator<LettersOnlyAdmits, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^[a-zA-ZñÑ]+$");
    }
}
