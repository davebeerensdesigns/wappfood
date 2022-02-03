package com.wappstars.wappfood.config;

import com.wappstars.wappfood.filter.JwtRequestFilter;
import com.wappstars.wappfood.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                // Our public endpoints
                .antMatchers(HttpMethod.POST,"/wp-json/wf/v1/authenticate").permitAll()
                .antMatchers(HttpMethod.GET,"/wp-json/wf/v1/authenticated").permitAll()
                .antMatchers(HttpMethod.GET,"/wp-json/wf/v1/products").permitAll()
                .antMatchers(HttpMethod.GET,"/wp-json/wf/v1/categories").permitAll()
                .antMatchers(HttpMethod.GET,"/wp-json/wf/v1/media/download/**").permitAll()
                // Our private endpoints
                .antMatchers(HttpMethod.GET,"/wp-json/wf/v1/authenticated").authenticated()
                .antMatchers("/wp-json/wf/v1/users/*/authorities").hasRole("ADMIN")
                .antMatchers("/wp-json/wf/v1/users").hasAnyRole("ADMIN", "SHOPMANAGER")
                .antMatchers("/wp-json/wf/v1/products").hasAnyRole("ADMIN", "SHOPMANAGER")
                .antMatchers("/wp-json/wf/v1/categories").hasAnyRole("ADMIN", "SHOPMANAGER")
                .antMatchers(HttpMethod.GET,"/wp-json/wf/v1/customers").hasAnyRole("ADMIN", "SHOPMANAGER", "EMPLOYEE")
                .antMatchers("/wp-json/wf/v1/customers").hasAnyRole("ADMIN", "SHOPMANAGER")
                .antMatchers(HttpMethod.GET,"/wp-json/wf/v1/orders").hasAnyRole("ADMIN", "SHOPMANAGER", "EMPLOYEE")
                .antMatchers(HttpMethod.PATCH,"/wp-json/wf/v1/orders/*/status").hasAnyRole("ADMIN", "SHOPMANAGER", "EMPLOYEE")
                .antMatchers("/wp-json/wf/v1/orders").hasAnyRole("ADMIN", "SHOPMANAGER")
                .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(
                jwtRequestFilter,
                UsernamePasswordAuthenticationFilter.class
        );

    }

}