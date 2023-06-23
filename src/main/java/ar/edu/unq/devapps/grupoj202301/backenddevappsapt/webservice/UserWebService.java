package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.UserListDto;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.GenericService;
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
@RequestMapping("/user")
public class UserWebService {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@Valid @RequestBody User user){
        return ResponseEntity.ok(userService.registerElement(user));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UserListDto>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }
}
