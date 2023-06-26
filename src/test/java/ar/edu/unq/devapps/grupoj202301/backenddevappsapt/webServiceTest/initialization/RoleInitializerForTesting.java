package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.initialization;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.Role;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.RolePersistence;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class RoleInitializerForTesting {
        protected final Log logger = LogFactory.getLog(getClass());

        @Autowired
        private RolePersistence rolePersistence;

        protected void startInitialization() throws IOException {
            logger.warn("TEST - Initializing Roles");
            List<String> roleNamesList = List.of("ADMIN","USER");

            for (String roleName : roleNamesList) {
                registerElement(roleName);
            }
        }

        public void registerElement(String roleName) {
            Role role = new Role(roleName);
            rolePersistence.save(role);
        }
}
