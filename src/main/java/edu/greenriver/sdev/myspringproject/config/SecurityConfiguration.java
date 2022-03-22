package edu.greenriver.sdev.myspringproject.config;

import edu.greenriver.sdev.myspringproject.services.LoginService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * this class is responsible for security configuration
 * @author Ruslan Bessarab
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private LoginService service;
    private BCryptPasswordEncoder encoder;

    public SecurityConfiguration(LoginService service, BCryptPasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(service)
                .passwordEncoder(encoder);
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity
                .ignoring().antMatchers("/resources/**")
                .and()
                .ignoring().antMatchers("/h2-console/**");
    }

    //accessing pages
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/detailing/admin")
                        .hasAnyAuthority("ROLE_ADMIN")
                    .antMatchers("/detailing/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                    .antMatchers("/**")
                        .permitAll()
                .and()
                    .formLogin()
                .and()
                    .logout()
                .and()
                    .exceptionHandling()
                        .accessDeniedPage("/denied");
    }
}
