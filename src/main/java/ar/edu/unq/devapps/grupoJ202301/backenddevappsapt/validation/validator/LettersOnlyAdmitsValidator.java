package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.validation.validator;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.validation.anotation.LettersOnlyAdmits;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class LettersOnlyAdmitsValidator implements ConstraintValidator<LettersOnlyAdmits, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^[a-zA-ZñÑ]+$");
    }
}
