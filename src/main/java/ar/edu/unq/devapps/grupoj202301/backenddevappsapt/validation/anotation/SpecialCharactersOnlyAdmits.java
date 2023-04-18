package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.anotation;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.validator.SpecialCharactersOnlyAdmitsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@SuppressWarnings("unused")
@Constraint(validatedBy = SpecialCharactersOnlyAdmitsValidator.class)
public @interface SpecialCharactersOnlyAdmits {
    String message() default "Only letters, numbers and special characters are allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}