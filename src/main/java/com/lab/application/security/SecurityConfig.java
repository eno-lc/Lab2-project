package com.lab.application.security;


import com.lab.application.service.impl.UserDetailsServiceImpl;
import com.lab.application.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig extends VaadinWebSecurity {


    private final UserDetailsServiceImpl myUserDetailsService;

    public SecurityConfig(UserDetailsServiceImpl myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/images/**")
                .permitAll()
                        .and()
                .userDetailsService(myUserDetailsService);

        super.configure(http);

        setLoginView(http, LoginView.class);

        return http.build();
    }

}
