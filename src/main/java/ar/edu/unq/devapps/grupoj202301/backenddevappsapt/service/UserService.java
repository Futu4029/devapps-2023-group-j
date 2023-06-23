package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.UserListDto;

import java.util.List;

public interface UserService  extends GenericService<User>{
    List<UserListDto> getUsers();
}
