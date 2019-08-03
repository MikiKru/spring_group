package pl.taskmanager.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // adresy wymagające logowania
                .antMatchers("/projects")
                .hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                // pozostałe nie wymagają logowania
                .anyRequest().permitAll()
                // wstrzyknięcie podstawowego formularza logowania
                .and()
                    .formLogin()
                        .and()
                    .httpBasic();

    }
}
