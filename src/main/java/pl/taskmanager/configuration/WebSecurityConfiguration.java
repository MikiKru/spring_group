package pl.taskmanager.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // adresy wymagające logowania
                .antMatchers("/projects").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .antMatchers("/project**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .antMatchers("/addProject").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/projects&delete**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/addTask**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/task&delete**").hasAnyAuthority("ROLE_ADMIN")
                // pozostałe nie wymagają logowania
                .anyRequest().permitAll()
                // wstrzyknięcie podstawowego formularza logowania
                .and()
                    .formLogin()
                        // adres formularza logowania
                        .loginPage("/login")
                        // nazwy zmiennych z formularza (th:name)
                        .usernameParameter("email")
                        .passwordParameter("password")
                        // adres przekazania wartości po wysłaniu formularza
                        // th:action
                        .loginProcessingUrl("/login-process")
                        // przekierowanie po zalogowaniu
                        .defaultSuccessUrl("/projects");


    }

}
