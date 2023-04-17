package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.webservice;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.validation.UserException;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        try{
            return ResponseEntity.ok(userService.register(user));
        } catch (UserException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
