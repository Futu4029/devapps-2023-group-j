package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.GenericSystemElement;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.GenericService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ElementAlreadyRegisteredException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@SuppressWarnings("unchecked")
public class Aspects {
    protected final Log logger = LogFactory.getLog(getClass());

    @Pointcut("execution(* ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.*.registerElement(..))")
    public void registerElementPointcut() {}

    @Pointcut("execution(* ar.edu.unq.devapps.grupoj202301.backenddevappsapt.initializer.*.registerElement(..))")
    public void registerElementInitializer() {}

    @Before("registerElementPointcut()")
    public void beforeRegisterElement(JoinPoint joinPoint) {
        GenericService<Object> service = (GenericService<Object>) joinPoint.getTarget();
        GenericSystemElement argument = (GenericSystemElement) Arrays.stream(joinPoint.getArgs()).findAny().get();
        if(service.elementIsPresent(argument)) {
            throw new ElementAlreadyRegisteredException("ERROR: The " + argument.getClass().getName() + " "
                                                                      + argument.getId() + " is already registered.");
        }
    }

    @Before("registerElementInitializer()")
    public void beforeRegisterElementInitializer(JoinPoint joinPoint) {
        List<String> lista = new ArrayList<>();
    }
}
