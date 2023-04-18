package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.validator;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation.NumbersOnlyAdmits;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class NumbersOnlyAdmitsValidator implements ConstraintValidator<NumbersOnlyAdmits, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^[\\d]+$");
    }
}
