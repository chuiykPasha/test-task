package test.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    @Autowired
    private AuthService authService;

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {
        String path = new UrlPathHelper().getPathWithinApplication(request);
        System.out.println("PATH " + path);
        System.out.println(request.getHeader("Authorization"));

        if(path.equals("/login")){
            try {
            authService.auth(request.getHeader("Authorization"));
            } catch (HttpClientErrorException.Unauthorized e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (ResourceAccessException e){
             response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            } catch (HttpServerErrorException.InternalServerError e){
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("TEST");
        super.afterPropertiesSet();
    }
}
