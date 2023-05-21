package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.aspects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Aspects {
    @Before("@within(org.springframework.stereotype.Service) && execution(* register*(..))")
    public void verifyRegister(JoinPoint jp) {

    }
}
