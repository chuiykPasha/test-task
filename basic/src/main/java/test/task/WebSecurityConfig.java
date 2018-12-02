package test.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(getBasicAuthEntryPoint());*/

        http
            //.csrf().disable()
            .authorizeRequests()
                .anyRequest().authenticated()
            .and()
            .authenticationProvider(provider())
            .httpBasic().realmName("MY-APP");

    }

    @Bean
    public AuthenticationProvider provider(){
        return new ExternalServiceAuthProvider();
    }



}
