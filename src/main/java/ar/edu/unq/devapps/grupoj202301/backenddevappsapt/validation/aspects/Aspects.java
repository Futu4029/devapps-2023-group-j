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
    @After("@within(org.springframework.web.bind.annotation.RestController) && execution(* getInformationFrom*(..)) && args(transactionDataDTO)")
    public void verifyThatAUserIsPartOfTheTransaction(JoinPoint jp, TransactionDataDTO transactionDataDTO) {
        Object[] args = jp.getArgs();
        if (args.length > 0) {
            Object arg = args[0];
            if (arg instanceof ArrayList) {
                ArrayList<Object> result = (ArrayList<Object>) arg;
                User user = (User) result.get(0);
                TransactionRequest transactionRequest = (TransactionRequest) result.get(1);
                String ownerUserEmail = transactionRequest.getUserOwner().getEmail();
                String otherUserEmail = transactionRequest.getUserSecondary().getEmail();
                if((!user.getEmail().equals(ownerUserEmail) && !user.getEmail().equals(otherUserEmail)) ||
                        ownerUserEmail.equals(otherUserEmail)) {
                    throw new NoValidUserTransactionException("The user who tries to participate in the operation does not belong to the same or tried to be both roles.");
                }
            }
        }
    }
}
