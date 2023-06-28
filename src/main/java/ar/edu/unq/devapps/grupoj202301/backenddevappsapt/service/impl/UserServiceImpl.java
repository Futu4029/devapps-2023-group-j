package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.Role;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.UserListDto;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoAuthResponse;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoLogin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.RolePersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.UserPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.security.JwtTokenProvider;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.UserException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RolePersistence rolePersistence;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public String registerElement(@Valid User user) {
        User userResult = userPersistence.save(user);
        return userResult.getEmail();
    }

    @Override
    public String registerElement(@Valid User user, String roleValue) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        Role role = rolePersistence.findByName(roleValue).get();
        user.setRoles(List.of(role));
        return registerElement(user);
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserListDto> getUsers() {
        List<User> userList = userPersistence.findAll();
        List<UserListDto> returnList = new ArrayList<>();
        userList.forEach(user -> returnList.add(new UserListDto(user)));
        return returnList;
    }

    @Override
    public DtoAuthResponse validateLoginData(DtoLogin dtoLogin) {
        User user = userPersistence.findById(dtoLogin.getEmail()).get();
        if(passwordEncoder.matches(dtoLogin.getPassword(), user.getPassword())) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dtoLogin.getEmail(), dtoLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            return new DtoAuthResponse(token);
        } else {
            throw new UserException("ERROR: The data entered is incorrect");
        }
    }
}
