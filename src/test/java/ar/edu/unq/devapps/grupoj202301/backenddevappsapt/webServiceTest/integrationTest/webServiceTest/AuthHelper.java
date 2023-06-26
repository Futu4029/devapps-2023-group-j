package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.integrationTest.webServiceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoAuthResponse;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AuthHelper {
    private String HTTP_LOCALHOST;
    private int port;
    private TestRestTemplate restTemplate;

    protected void setData(String HTTP_LOCALHOST, int port, TestRestTemplate restTemplate) {
        this.HTTP_LOCALHOST = HTTP_LOCALHOST;
        this.port = port;
        this.restTemplate = restTemplate;
    }

    protected ResponseEntity<String> registerUser(User user) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.CONTENT_TYPE, Lists.newArrayList(MediaType.APPLICATION_JSON_VALUE));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        var http = new HttpEntity(json, headers);
        return restTemplate.exchange(HTTP_LOCALHOST + port + "/auth/register", HttpMethod.POST, http, String.class);
    }

    protected ResponseEntity<String> loginUser(DtoLogin dtoLogin) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.CONTENT_TYPE, Lists.newArrayList(MediaType.APPLICATION_JSON_VALUE));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(dtoLogin);
        var http = new HttpEntity(json, headers);
        return restTemplate.exchange(HTTP_LOCALHOST + port + "/auth/login", HttpMethod.POST, http, String.class);
    }

    protected String getToken(DtoLogin dtoLogin) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.CONTENT_TYPE, Lists.newArrayList(MediaType.APPLICATION_JSON_VALUE));
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(dtoLogin);
        var http = new HttpEntity(json, headers);
        ParameterizedTypeReference<DtoAuthResponse> responseType = new ParameterizedTypeReference<DtoAuthResponse>() {};
        DtoAuthResponse response = restTemplate.exchange(HTTP_LOCALHOST + port + "/auth/login", HttpMethod.POST, http, responseType).getBody();
        assert response != null;
        return response.getAccessToken();
    }
}
