package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.service;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.validation.exception.UserException;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model.User;

public interface UserService {
    String register(User user) throws UserException;
}
