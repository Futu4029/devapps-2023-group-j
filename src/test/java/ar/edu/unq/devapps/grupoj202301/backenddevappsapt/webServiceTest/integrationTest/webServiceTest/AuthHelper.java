package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.integrationTest.webServiceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoAuthResponse;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthHelper {

    @Autowired
    private TestRestTemplate restTemplate;

    public void registerUser() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.CONTENT_TYPE, Lists.newArrayList(MediaType.APPLICATION_JSON_VALUE));
        var json = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/newUser.json")));
        var http = new HttpEntity(json, headers);
        restTemplate.exchange("/auth/register", HttpMethod.POST, http, String.class);
    }

    public String getToken() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.CONTENT_TYPE, Lists.newArrayList(MediaType.APPLICATION_JSON_VALUE));
        var json = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/loginCredential.json")));
        var http = new HttpEntity(json, headers);
        ParameterizedTypeReference<DtoAuthResponse> responseType = new ParameterizedTypeReference<DtoAuthResponse>() {};
        DtoAuthResponse response = restTemplate.exchange("/auth/login", HttpMethod.POST, http, responseType).getBody();
        assert response != null;
        return response.getAccessToken();
    }


}
