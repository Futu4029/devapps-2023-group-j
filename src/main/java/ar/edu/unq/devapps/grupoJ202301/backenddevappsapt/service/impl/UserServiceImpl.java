package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.persistence.UserPersistence;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPersistence userPersistence;

    @Override
    public String register(User user){
        User _user = userPersistence.save(user);
        return _user.getEmail();
    }
}
