package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.initialization;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.Role;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.RolePersistence;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@Profile("test")
public class RoleInitializer {

        @Autowired
        private RolePersistence rolePersistence;

        @PostConstruct
        public void initialize() throws IOException {
            startInitialization();
        }

        private void startInitialization() throws IOException {
            List<String> roleNamesList = List.of("ADMIN","USER");

            for (String roleName : roleNamesList) {
                registerElement(roleName);
            }
        }

        public void registerElement(String roleName) throws IOException {
            Role role = new Role(roleName);
            rolePersistence.save(role);
        }
}
