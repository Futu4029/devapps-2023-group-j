package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.GenericSystemElement;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.GenericService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ElementAlreadyRegisteredException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ElementNotRegisteredException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
@SuppressWarnings("unchecked")
public class Aspects {
    protected final Log logger = LogFactory.getLog(getClass());

    @Pointcut("execution(* ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.*.registerElement(..))")
    public void registerElementPointcut() {}

    @Pointcut("execution(* ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.*.*(..)) && (execution(* *find*(..)) || execution(* *Find*(..)))")
    public void findElementPointCut() {}

    @Before("registerElementPointcut()")
    public void beforeRegisterElement(JoinPoint joinPoint) {
        GenericService<Object> service = (GenericService<Object>) joinPoint.getTarget();
        GenericSystemElement argument = (GenericSystemElement) Arrays.stream(joinPoint.getArgs()).findAny().get();
        if(service.elementIsPresent(argument)) {
            String className = argument.getClass().getName();
            String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
            throw new ElementAlreadyRegisteredException("ERROR: The " + simpleClassName + " "
                                                                      + argument.getId() + " is already registered.");
        }
    }

    @AfterReturning(pointcut = "findElementPointCut()", returning = "result")
    public void beforeFindElement(JoinPoint joinPoint, Object result) {
        if (result == null || result instanceof Optional && !((Optional<?>) result).isPresent()) {
            String argument = (String) joinPoint.getArgs()[0];
            throw new ElementNotRegisteredException("Error: The element with id " + argument + " is not present");
        }
    }
}
