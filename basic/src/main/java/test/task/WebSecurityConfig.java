package test.task;

import com.allanditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(getBasicAuthEntryPoint());*/


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

                http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).and().httpBasic().and().authorizeRequests().antMatchers("/login").authenticated();
                //http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
                //http.csrf().csrfTokenRepository(csrfTokenRepository());

        //http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
        //http
           // .authorizeRequests()
             //   .anyRequest().authenticated()
            /*.and()
            .csrf()
            .csrfTokenRepository(csrfTokenRepository())*/
        ;

    }


    @Bean
    public AuthenticationProvider provider(){
        return new ExternalServiceAuthProvider();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }
}
