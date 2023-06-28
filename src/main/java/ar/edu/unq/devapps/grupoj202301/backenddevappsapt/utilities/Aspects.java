package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.GenericSystemElement;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.GenericService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ElementAlreadyRegisteredException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ElementNotRegisteredException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@Aspect
@Component
@SuppressWarnings("unchecked")
public class Aspects {
    private static final Logger logger = LoggerFactory.getLogger(Aspects.class);

    @Pointcut("execution(* ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.*.registerElement(..))")
    public void registerElementPointcut() {}

    @Pointcut("execution(* ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.*.*(..)) && (execution(* *find*(..)) || execution(* *Find*(..)))")
    public void findElementPointCut() {}

    @Pointcut("execution(* ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice.*.*(..))")
    public void webServiceMethod() {}

    @Pointcut("execution(* ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.GlobalExceptionHandler.*Exception*(..))")
    public void globalException() {}

    @Before("globalException()")
    public void globalException(JoinPoint joinPoint) {
        Optional<Object> result = Arrays.stream(joinPoint.getArgs()).findAny();
        if(result.isPresent()) {
            Exception exception = (Exception) result.get();
            String method = exception.getStackTrace()[0].getMethodName().replace("before", "");
            String message = exception.getMessage();
            String name = exception.getClass().getName();
            logger.error("Exception triggered By: " + method.substring(0, 1).toLowerCase() + method.substring(1) + " More info below: ", exception);
        } else {
            logger.error("The exception was unreachable");
        }
    }

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

    @Around("webServiceMethod()")
    public Object afterMethodExecutionWebService(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalDateTime localDateTime = java.time.LocalDateTime.now();
        String id = "PublicUser";
        Object parameters = "No Parameters";
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        if(joinPoint.getArgs().length > 0) {
            Optional<Object> potentialResult = Arrays.stream(joinPoint.getArgs()).findAny();
            if(potentialResult.isPresent()) {
                parameters = potentialResult.get().toString();
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        logger.debug(localDateTime + " - " + id + " - " + methodName + " - " + parameters + " - Execution time: " + elapsedTime + " ms");
        return result;
    }
}
