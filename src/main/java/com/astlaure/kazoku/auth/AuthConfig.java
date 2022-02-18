package com.astlaure.kazoku.auth;

import com.astlaure.kazoku.auth.handlers.LoginFailureHandler;
import com.astlaure.kazoku.auth.handlers.LoginSuccessHandler;
import com.astlaure.kazoku.auth.handlers.LogoutSuccessHandler;
import com.astlaure.kazoku.users.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class AuthConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .authorizeRequests()
            .antMatchers("/auth/profile").authenticated()
            .antMatchers("/users/**").authenticated()
            .antMatchers("/transactions/**").authenticated()
            .anyRequest().permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
            .and()
            .formLogin()
            .loginProcessingUrl("/auth/login")
            .successHandler(new LoginSuccessHandler())
            .failureHandler(new LoginFailureHandler())
            .and()
            .logout()
            .logoutUrl("/auth/logout")
            .logoutSuccessHandler(new LogoutSuccessHandler());
    }
}
