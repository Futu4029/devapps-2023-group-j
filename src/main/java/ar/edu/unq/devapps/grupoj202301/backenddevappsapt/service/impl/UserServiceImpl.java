package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
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
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPersistence userPersistence;

    @Override
    public String register(User user) throws UserException {
        String field;
        String message;
        if(userPersistence.findById(user.getEmail()).isEmpty()) {
            try {
                User _user = userPersistence.save(user);
                return _user.getEmail();
            } catch (TransactionSystemException exception) {
                ConstraintViolation<?> result = ((ConstraintViolationException) Objects.requireNonNull(exception.getRootCause())).getConstraintViolations().stream().findAny().get();
                field = result.getPropertyPath().toString();
                message = result.getMessage();
            }
        } else {
            field = "email";
            message = "Already in used";
        }
        throw new UserException("Field " + field + " has an error: " + message);
    }
}
