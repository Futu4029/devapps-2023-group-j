package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public final class SecurityUtils {
    private SecurityUtils(){}

    public static User getLoggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}