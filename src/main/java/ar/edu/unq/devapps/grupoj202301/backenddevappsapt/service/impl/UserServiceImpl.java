package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.UserPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
