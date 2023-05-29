package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.initializer;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Profile("!test")
public class UserInitializer {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driver-class-name:NONE}")
    private String className;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize(){
        String HSQLDB_CLASS_NAME = "org.hsqldb.jdbc.JDBCDriver";

        if (className.equals(HSQLDB_CLASS_NAME)){
            logger.warn("Init Data Using HSQLDB DataBase - Initializing Users");
            startInitialization();
        }else{
            logger.warn("No database was set for the initialization");
        }
    }

    private void startInitialization() {
        //User user = new User("user0@example.com", "00000000", "Name0", "Surname0", "Address123", "password1!", "0123456789012345678900");
        User user1 = new User("user1@example.com", "00000001", "Name", "Surname", "Address123", "passworD1!", "0123456789012345678901");
        //userService.register(user);
        userService.register(user1);
    }


}
