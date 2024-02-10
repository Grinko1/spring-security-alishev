package com.security.FirstSecurityApp.config;

import com.security.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig( PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider);
        auth.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
    }

protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
            .requestMatchers("/auth/login", "/auth/registration", "error").permitAll() // Permit access to login page
            .anyRequest().hasAnyRole("USER", "ADMIN") // All other requests require authentication
            .and()
            .formLogin()
            .loginPage("/auth/login") // Specify custom login page
            .loginProcessingUrl("/hello") // Specify the URL to submit the login form
            .defaultSuccessUrl("/hello", true) // Redirect to this URL upon successful login
            .failureUrl("/auth/login?error")// Redirect to this URL upon login failure
            .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");

}


}
