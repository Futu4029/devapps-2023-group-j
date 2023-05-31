package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.UserPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.GenericService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements GenericService<User> {

    @Autowired
    private UserPersistence userPersistence;

    @Override
    public String registerElement(@Valid User user) {
        User userResult = userPersistence.save(user);
        return userResult.getEmail();
    }

    @Override
    public Optional<User> findElementById(String elementId) {
        return userPersistence.findById(elementId);
    }

    @Override
    public boolean elementIsPresent(User user) {
        return userPersistence.existsById(user.getEmail());
    }
}
