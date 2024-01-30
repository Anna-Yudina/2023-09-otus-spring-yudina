package ru.otus.hw.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.otus.hw.services.PersonService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final PersonService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/admin").hasRole("ADMIN");
                    request.requestMatchers("/login").permitAll();
                    request.anyRequest().hasAnyRole("USER", "ADMIN");
                })
                .userDetailsService(userService)
                .formLogin(fL -> fL.loginPage("/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/books"))
                .logout(logout -> logout.logoutUrl("/logout"))
                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
