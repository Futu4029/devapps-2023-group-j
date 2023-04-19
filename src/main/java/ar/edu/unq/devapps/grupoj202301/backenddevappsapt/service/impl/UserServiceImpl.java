package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.BusinessException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.UserException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.UserPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPersistence userPersistence;

    @Override
    public String register(User user) throws UserException {
        String field = "email";
        String message = "Already in used";
        if(userPersistence.findById(user.getEmail()).isEmpty()) {
            try {
                User userResult = userPersistence.save(user);
                return userResult.getEmail();
            } catch (TransactionSystemException exception) {
                Throwable rootCause = exception.getRootCause();
                if(rootCause instanceof ConstraintViolationException) {
                    Optional<ConstraintViolation<?>> optionalResult = ((ConstraintViolationException) (rootCause)).getConstraintViolations().stream().findAny();
                    if(optionalResult.isPresent()) {
                        field = optionalResult.get().getPropertyPath().toString();
                        message = optionalResult.get().getMessage();
                    }
                }
            } catch (RuntimeException exception) {
                throw new BusinessException("Error trying to register a user");
            }
        }
        throw new UserException("Field " + field + " has an error: " + message);
    }
}
