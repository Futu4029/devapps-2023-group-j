package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.validation.validator;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.validation.anotation.SpecialCharactersOnlyAdmits;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class SpecialCharactersOnlyAdmitsValidator implements ConstraintValidator<SpecialCharactersOnlyAdmits, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$");
    }
}