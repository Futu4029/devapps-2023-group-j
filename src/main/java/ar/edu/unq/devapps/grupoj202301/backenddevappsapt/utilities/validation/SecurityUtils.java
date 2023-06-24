package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.UserDetailsDto;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
    private SecurityUtils(){}

    public static UserDetailsDto getLoggedInUser() {
        return (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}