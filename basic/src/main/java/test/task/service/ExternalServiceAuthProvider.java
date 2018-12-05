package test.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import test.task.service.AuthService;

public class ExternalServiceAuthProvider implements AuthenticationProvider {

    @Autowired
    private AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            authService.auth(login, password);
        } catch (HttpClientErrorException.Unauthorized e){
            throw new BadCredentialsException("BC");
            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (ResourceAccessException e){
            throw new BadCredentialsException("BC");
            //response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        } catch (HttpServerErrorException.InternalServerError e){
            throw new BadCredentialsException("BC");
            //response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                authentication.getCredentials(), AuthorityUtils.createAuthorityList("ROLE_USER"));
        result.setDetails(authentication.getDetails());

        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
