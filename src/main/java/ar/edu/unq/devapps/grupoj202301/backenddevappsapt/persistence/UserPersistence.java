package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPersistence extends JpaRepository<User, String> {
}
