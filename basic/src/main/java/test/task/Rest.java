package test.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import test.task.exception.InternalServerErrException;
import test.task.exception.NotFoundException;
import test.task.exception.ServiceUnavailableException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@RestController
@RequestMapping("/")
public class Rest {
    @Value("${data.login}")
    private String dataLogin;
    @Value("${data.password}")
    private String dataPassword;
    @Value("${data.host}")
    private String dataHost;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpSession httpSession, HttpServletResponse httpServletResponse){
        httpServletResponse.addHeader("X-CSRF-TOKEN", ((DefaultCsrfToken)httpSession.getAttribute("org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN")).getToken());

        return "Done";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "Done";
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String grades(@PathVariable("userId") String userId){
        RestTemplate restTemplate = new RestTemplate();
        final String dataPath
                = "http://" + dataHost + "/data/" + userId;
        ResponseEntity<String> response = null;

        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(null, Rest.fillHeaders(dataLogin, dataPassword));
            response = restTemplate.postForEntity( dataPath, request , String.class );

            if(response.getBody() == null){
                throw new NotFoundException();
            }

            return String.valueOf(response.getBody());
        }catch (ResourceAccessException e){
            throw new ServiceUnavailableException();
        }catch (HttpClientErrorException.Forbidden e){
            throw new InternalServerErrException();
        } catch (HttpClientErrorException.NotFound e){
            throw new NotFoundException();
        }
    }

    public static HttpHeaders fillHeaders(String login, String password){
        String cred = login + ":" + password;
        System.out.println("CREED " + cred);
        String base64Credentials = new String(org.apache.commons.codec.binary.Base64.encodeBase64(cred.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
