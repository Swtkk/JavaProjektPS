package com.example.store.projektstore.Security;

//import com.example.store.projektstore.Service.CustomUserDetailsService;

import com.example.store.projektstore.Service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


@Configuration
@EnableWebSecurity
public class SecuritConfig {
    private final CustomUserDetailsService usersDetailsService;

    public SecuritConfig(CustomUserDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/", "/login", "/register").permitAll() // Umożliwia dostęp do tych endpointów dla wszystkich
                        .requestMatchers("/users").hasAuthority("admin") // Tylko admini mają dostęp do /users
                        .anyRequest().authenticated() // Wszystkie inne żądania wymagają autentykacji
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // Przekierowanie na stronę główną po wylogowaniu
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID") // Usuwanie cookies sesji
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/") // Przekierowanie na stronę główną w przypadku nieprawidłowej sesji
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore((request, response, chain) -> {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication != null && authentication.isAuthenticated()) {
                        System.out.println("Authenticated user: " + authentication.getName());
                        System.out.println("Authenticated role" + authentication.getAuthorities());
                    } else {
                        System.out.println("No authenticated user");
                    }

                    chain.doFilter(request, response);
                }, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(usersDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
