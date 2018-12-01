package test.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Service
public class AuthService {
    @Value("${auth.login}")
    private String authLogin;
    @Value("${auth.password}")
    private String authPassword;
    private final int LOGIN = 0;
    private final int PASSWORD = 1;
    @Value("${auth.host}")
    private String authHost;

    public void auth(String header){
        System.out.println("CALL AUTH " + Rest.getCredentials(header));
        String[] credentials = Rest.getCredentials(header);
        RestTemplate restTemplate = new RestTemplate();
        final String authPath
                = "http://" + authHost + "/auth";

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("login", credentials[LOGIN]);
        map.add("password", credentials[PASSWORD]);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, Rest.fillHeaders(authLogin, authPassword));

        ResponseEntity<String> response = restTemplate.postForEntity( authPath, request , String.class );
    }
}
