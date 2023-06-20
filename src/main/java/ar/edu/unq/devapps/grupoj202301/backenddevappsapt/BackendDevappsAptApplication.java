package ar.edu.unq.devapps.grupoj202301.backenddevappsapt;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Crypto API", version = "1.0", description = "First approach"))
@EnableAspectJAutoProxy
@ComponentScan("ar.edu.unq.devapps.grupoj202301.backenddevappsapt")
public class BackendDevappsAptApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendDevappsAptApplication.class, args);
	}
}
