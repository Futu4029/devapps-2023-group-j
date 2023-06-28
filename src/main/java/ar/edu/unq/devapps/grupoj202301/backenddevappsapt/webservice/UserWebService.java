package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.UserListDto;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoAuthResponse;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoLogin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserWebService {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user){
        return ResponseEntity.ok(userService.registerElement(user,"USER"));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody User user){
        return ResponseEntity.ok(userService.registerElement(user, "ADMIN"));
    }

    @PostMapping("/login")
    public ResponseEntity<DtoAuthResponse> login(@Valid @RequestBody DtoLogin dtoLogin) {
        return ResponseEntity.ok(userService.validateLoginData(dtoLogin));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UserListDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
