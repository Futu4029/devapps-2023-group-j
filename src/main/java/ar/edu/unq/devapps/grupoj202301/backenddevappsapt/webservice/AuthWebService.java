package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.Role;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoAuthResponse;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoLogin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.RolePersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.security.JwtTokenProvider;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthWebService {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private RolePersistence rolePersistence;

    @Autowired
    public AuthWebService(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RolePersistence rolePersistence) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.rolePersistence = rolePersistence;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        Role role = rolePersistence.findByName("USER").get();
        user.setRoles(List.of(role));
        return new ResponseEntity<>(userService.registerElement(user), HttpStatus.CREATED);
    }


    @PostMapping("/registerAdm")
    public ResponseEntity<String> registerAdmin(@RequestBody User user){
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        Role role = rolePersistence.findByName("ADMIN").get();
        user.setRoles(Collections.singletonList(role));
        return new ResponseEntity<>(userService.registerElement(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<DtoAuthResponse> login(@RequestBody DtoLogin dtoLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getEmail(), dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return new ResponseEntity<>(new DtoAuthResponse(token), HttpStatus.OK);
    }
}
