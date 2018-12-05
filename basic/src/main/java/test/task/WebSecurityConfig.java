package test.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import javax.servlet.http.HttpServletRequest;

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
                csrfTokenRepository.setSessionAttributeName("X-CSRF-TOKEN");
                http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).and().httpBasic().and().authorizeRequests().antMatchers("/login").authenticated()
                .and().authenticationProvider(provider());
                http.csrf().csrfTokenRepository(csrfTokenRepository);
    }


    @Bean
    public AuthenticationProvider provider(){
        return new ExternalServiceAuthProvider();
    }
}
