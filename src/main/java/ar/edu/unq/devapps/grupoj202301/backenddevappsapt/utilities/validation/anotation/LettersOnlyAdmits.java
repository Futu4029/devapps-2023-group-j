package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.anotation;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.validator.LettersOnlyAdmitsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@SuppressWarnings("unused")
@Constraint(validatedBy = LettersOnlyAdmitsValidator.class)
public @interface LettersOnlyAdmits {
    String message() default "Only letters are allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}