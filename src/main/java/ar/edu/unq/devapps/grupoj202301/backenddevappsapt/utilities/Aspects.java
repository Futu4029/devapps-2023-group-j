package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.GenericSystemElement;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.GenericService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ElementAlreadyRegisteredException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ElementNotRegisteredException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
@SuppressWarnings("unchecked")
public class Aspects {
    static Logger logger = LoggerFactory.getLogger(Aspects.class);

    @Pointcut("execution(* ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.*.registerElement(..))")
    public void registerElementPointcut() {}

    @Pointcut("execution(* ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.*.*(..)) && (execution(* *find*(..)) || execution(* *Find*(..)))")
    public void findElementPointCut() {}

    @Before("registerElementPointcut()")
    public void beforeRegisterElement(JoinPoint joinPoint) {
        GenericService<Object> service = (GenericService<Object>) joinPoint.getTarget();
        Optional<Object> argument = Arrays.stream(joinPoint.getArgs()).findAny();
        if(argument.isEmpty()){
            throw new NoSuchElementException();
        };
        GenericSystemElement presentArgument = (GenericSystemElement) argument.get();
        String className = presentArgument.getClass().getName();
        String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
        if(service.elementIsPresent(presentArgument)) {
            throw new ElementAlreadyRegisteredException("ERROR: The " + simpleClassName + " "
                    + presentArgument.getId() + " is already registered.");
        }
        logger.info(simpleClassName + " class is persisted with ID: " + presentArgument.getId());
    }

    @AfterReturning(pointcut = "findElementPointCut()", returning = "result")
    public void beforeFindElement(JoinPoint joinPoint, Object result) {
        if (result == null || result instanceof Optional && !((Optional<?>) result).isPresent()) {
            String argument = (String) joinPoint.getArgs()[0];
            throw new ElementNotRegisteredException("Error: The element with id " + argument + " is not present");
        }
    }
}
