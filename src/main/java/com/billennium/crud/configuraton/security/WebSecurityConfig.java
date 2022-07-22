package com.billennium.crud.configuraton.security;

import com.billennium.crud.configuraton.security.filters.JSONWebTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(
                User.builder()
                        .username("admin")
                        .password(getPasswordEncoder().encode("admin"))
                        .roles("ADMIN")
        );
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.antMatcher("/api/**").authorizeRequests()
                .antMatchers("/api/**").hasAnyRole("ADMIN")
                .and()
                .addFilterBefore(new JSONWebTokenFilter(authenticationManager()), BasicAuthenticationFilter.class);

        http.authorizeRequests().antMatchers("/h2").permitAll();
    }
}
