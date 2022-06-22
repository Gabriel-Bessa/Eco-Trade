package com.ecotrade.mstrade.config.security;

import com.ecotrade.mstrade.config.PropertiesConfig;
import com.ecotrade.mstrade.config.security.filter.AuthenticationFilter;
import com.ecotrade.mstrade.config.security.filter.AuthorizationFilter;
import com.ecotrade.mstrade.config.security.handle.AccessDeniedHandler;
import com.ecotrade.mstrade.service.NoSqlService;
import com.ecotrade.mstrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccessDeniedHandler accessDeniedHandler;
    private final UserDetailsService userDetailsService;
    private final AuthorizationFilter authorizationFilter;
    private final NoSqlService noSqlService;
    private final PropertiesConfig config;
    private final BCryptPasswordEncoder encoder;
    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.logout().deleteCookies("JSESSIONID");
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        http.csrf().disable().authorizeRequests().anyRequest().permitAll().and().cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.addAllowedOrigin("*");
            return config;
        });
        http.addFilter(new AuthenticationFilter(authenticationManagerBean(), config, noSqlService, userService));
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
