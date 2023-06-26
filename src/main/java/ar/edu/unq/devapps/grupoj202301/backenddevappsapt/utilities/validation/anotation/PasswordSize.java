package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.anotation;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.validator.PasswordSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@SuppressWarnings("unused")
@Constraint(validatedBy = PasswordSizeValidator.class)
public @interface PasswordSize {
    String message() default "size must be between 6 and 32";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
