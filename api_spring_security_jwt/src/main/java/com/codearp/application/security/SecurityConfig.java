package com.codearp.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("test")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // permitir H2 Console
                        .requestMatchers("/h2-console/**").permitAll()
                        // el resto requiere autenticación
                        .anyRequest().authenticated()
                )
                // desactivar CSRF solo para H2
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                // permitir frames (para que cargue bien la consola en el navegador)
                .headers(headers -> headers.frameOptions( HeadersConfigurer.FrameOptionsConfig::sameOrigin ) )
                // login básico por simplicidad
                .httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
}
