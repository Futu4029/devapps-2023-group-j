package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.UserListDto;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.UserPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPersistence userPersistence;

    @Override
    public String registerElement(@Valid User user) {
        User userResult = userPersistence.save(user);
        return userResult.getEmail();
    }

    @Override
    public void updateElement(User user) {
        if(elementIsPresent(user)) {
            userPersistence.save(user);
        }
    }

    @Override
    public Optional<User> findElementById(String elementId) {
        return userPersistence.findById(elementId);
    }

    @Override
    public boolean elementIsPresent(User user) {
        return userPersistence.existsById(user.getEmail());
    }

    @Override
    public List<UserListDto> getUsers() {
        List<User> userList = userPersistence.findAll();
        List<UserListDto> returnList = new ArrayList<>();
        userList.forEach(user -> returnList.add(new UserListDto(user)));
        return returnList;
    }
}
