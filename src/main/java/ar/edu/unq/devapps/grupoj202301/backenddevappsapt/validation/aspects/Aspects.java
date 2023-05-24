package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.aspects;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionDataDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.NoValidUserTransactionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Aspect
public class Aspects {

}
