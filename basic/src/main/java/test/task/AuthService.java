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

    public void auth(String header){
        System.out.println("CALL AUTH " + getCredentials(header));
        String[] credentials = getCredentials(header);
        RestTemplate restTemplate = new RestTemplate();
        String authPath
                = "http://localhost:8081/auth";

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("login", credentials[LOGIN]);
        map.add("password", credentials[PASSWORD]);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, fillHeaders());

        ResponseEntity<String> response = restTemplate.postForEntity( authPath, request , String.class );

    }

    private String[] getCredentials(String authorization){
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            return credentials.split(":", 2);
        }

        return null;
    }

    private HttpHeaders fillHeaders(){
        String cred = authLogin + ":" + authPassword;
        System.out.println("CREED " + cred);
        String base64Credentials = new String(org.apache.commons.codec.binary.Base64.encodeBase64(cred.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
