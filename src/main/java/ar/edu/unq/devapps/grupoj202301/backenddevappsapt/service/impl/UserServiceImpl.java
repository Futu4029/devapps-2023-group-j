package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.UserPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPersistence userPersistence;

    @Override
    public String register(@Valid User user) {
        if(userPersistence.findById(user.getEmail()).isEmpty()) {
            User userResult = userPersistence.save(user);
            return userResult.getEmail();
        } else {
            throw new DataIntegrityViolationException("Field email has an error: Already in used");
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userPersistence.findById(email).orElseThrow();
    }

    @Override
    public String confirmReception(User user, TransactionRequest transactionRequest) {
        User owner = transactionRequest.getUserOwner();
        User other = transactionRequest.getUserSecondary();
        long minutes = Duration.between(transactionRequest.getCreationDate(), LocalDateTime.now()).toMinutes();
        int reputationToAdd = 5;
        if(minutes <= 30) {
            reputationToAdd = reputationToAdd + 5;
        }
        other.recalculateScore(reputationToAdd);
        owner.recalculateScore(reputationToAdd);
        userPersistence.save(owner);
        userPersistence.save(other);

        return "Congratulations, the transaction was a success.";
    }

    @Override
    public String discountReputation(User user, TransactionRequest transactionRequest) {
        user.recalculateScore(-20);
        userPersistence.save(user);

        return "His reputation has diminished.";
    }

}
