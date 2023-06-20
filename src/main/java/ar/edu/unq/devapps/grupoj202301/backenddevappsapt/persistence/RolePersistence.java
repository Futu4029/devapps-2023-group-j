package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolePersistence extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
