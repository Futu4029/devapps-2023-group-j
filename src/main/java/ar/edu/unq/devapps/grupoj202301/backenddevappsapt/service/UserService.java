package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.UserListDto;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoAuthResponse;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoLogin;
import jakarta.validation.Valid;
import java.util.List;

public interface UserService  extends GenericService<User>{
    String registerElement(@Valid User user, String roleValue);
    List<UserListDto> getUsers();
    DtoAuthResponse validateLoginData(DtoLogin dtoLogin);
}
