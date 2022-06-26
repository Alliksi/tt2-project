package com.storage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/","/index","/login","/login-error","/register-company", "/error", "/js/**", "/css/**").permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .and().authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                    .and().authorizeRequests().antMatchers("/worker/**").access("hasRole('ROLE_WORKER')")
                    .and().authorizeRequests().antMatchers("/**").access("hasRole('ROLE_OWNER')")
                    .and().authorizeRequests().antMatchers("/profile/**").authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?wrong")
                    .defaultSuccessUrl("/index", true)
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                    .logoutSuccessUrl("/")
                    .logoutUrl("/logout").invalidateHttpSession(true)
                    .and()
                .headers()
                    .frameOptions()
                    .disable()
                    .and()
                .csrf()
                    .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

    // TODO create tests: https://betterprogramming.pub/spring-security-basic-login-form-7c8f6e6e9f56
}