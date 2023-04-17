package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.validation.anotation;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.validation.validator.NumbersOnlyAdmitsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumbersOnlyAdmitsValidator.class)
public @interface NumbersOnlyAdmits {
    String message() default "Only numbers are allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}