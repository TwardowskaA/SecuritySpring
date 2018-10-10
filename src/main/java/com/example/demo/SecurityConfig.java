package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/rejestracja").permitAll()
                .antMatchers("/logowanie").permitAll()
                .antMatchers("/edycja").authenticated()
                .antMatchers("/edycjaUzytkownika").authenticated()
                .antMatchers("/uzytkownicy").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().disable()
                .and()
                .formLogin()
                .loginPage("/logowanie");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select login, password, enabled from user where login=?")
                .authoritiesByUsernameQuery(
                        "select login, role from user_role where login=?")
                .passwordEncoder(passwordEncoder());
    }

}
