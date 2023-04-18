package ar.edu.unq.devapps.grupoj202301.backenddevappsapt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BackendDevappsAptApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendDevappsAptApplication.class, args);
	}

}
