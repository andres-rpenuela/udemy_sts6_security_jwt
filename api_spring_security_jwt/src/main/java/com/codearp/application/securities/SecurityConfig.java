package com.codearp.application.securities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

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
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/users/**") ).permitAll()
                        // el resto requiere autenticación
                        .anyRequest().authenticated()
                )
                // desactivar CSRF solo para H2
                //.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                // desactivar CSRF para simplificar las pruebas (no es recomendable en producción)
                .csrf(AbstractHttpConfigurer::disable)
                // permitir frames (para que cargue bien la consola en el navegador)
                .headers(headers -> headers.frameOptions( HeadersConfigurer.FrameOptionsConfig::sameOrigin ) )
                // login básico por simplicidad
                .httpBasic(Customizer.withDefaults())
                // Con esto la session http donde se guarda el usaurio por defecto es con estado, con esto hacemos
                // que sea , sin estado, es decir, que se debe authenticar en cada petición, ya sea mediante token,
                // formulario, ...
                .sessionManagement( managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        
        return http.build();
    }
}
