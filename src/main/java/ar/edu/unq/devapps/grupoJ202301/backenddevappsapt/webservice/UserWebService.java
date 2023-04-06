package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.webservice;

import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserWebService {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody User user){
        userService.register(user);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
