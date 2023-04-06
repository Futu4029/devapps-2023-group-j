package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.persistence;

import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPersistence extends JpaRepository<User, Long> {
}
