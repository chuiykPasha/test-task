package test.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        RequestMatcher csrfRequestMatcher = new RequestMatcher() {

            // Enabled CSFR protection on the following urls:
            private AntPathRequestMatcher[] requestMatchers = {
                    new AntPathRequestMatcher("/**/logout"),
                    new AntPathRequestMatcher("/**/user/***")
            };

            @Override
            public boolean matches(HttpServletRequest request) {
                // If the request match one url the CSFR protection will be enabled
                for (AntPathRequestMatcher rm : requestMatchers) {
                    if (rm.matches(request)) {
                        return true;
                    }
                }
                return false;
            }
        };// method matches

        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
                http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).and().httpBasic().and().authorizeRequests().antMatchers("/login").authenticated()
                .and().authenticationProvider(provider());
                http.csrf().csrfTokenRepository(csrfTokenRepository);
    }


    @Bean
    public AuthenticationProvider provider(){
        return new ExternalServiceAuthProvider();
    }
}
