package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer ";

    public DtoAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
