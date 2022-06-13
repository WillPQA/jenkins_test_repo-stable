package com.pqashed.shed;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) {
        try {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
            corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setExposedHeaders(List.of("Authorization"));

            http.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
                    .authenticated().and().csrf().disable().cors().configurationSource(request -> corsConfiguration);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}