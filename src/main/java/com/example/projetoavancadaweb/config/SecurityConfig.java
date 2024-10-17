package com.example.projetoavancadaweb.config;

import com.example.projetoavancadaweb.jwt.JwtAuthenticationEntryPoint;
import com.example.projetoavancadaweb.jwt.JwtAuthorizationFilter;
import com.example.projetoavancadaweb.security.UsuarioAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.POST;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final AuthenticationManager authenticationManager;
//
//    @Lazy
//    private final UsuarioAuthenticationFilter usuarioAuthenticationFilter;
//
//    public SecurityConfig(AuthenticationManager authenticationManager, @Lazy UsuarioAuthenticationFilter usuarioAuthenticationFilter) {
//        this.authenticationManager = authenticationManager;
//        this.usuarioAuthenticationFilter = usuarioAuthenticationFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/usuarios/**").permitAll()
//                        .anyRequest().denyAll())
//                .addFilterBefore(usuarioAuthenticationFilter, UsuarioAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    @Lazy
//    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
//        return new UsernamePasswordAuthenticationFilter(authenticationManager);
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

@EnableMethodSecurity
@EnableWebMvc
@Configuration
public class SecurityConfig {

    public static final String[] DOCUMENTATION_OPENAPI = {
            "/docs/index.html",
            "/docs-park.html" , "/docs-park/**",
            "/v3/api-docs/**",
            "/swagger-ui-custom.hmtl", "/swagger-ui.html", "/swagger-ui/**",
            "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(POST, "api/usuarios").permitAll()
                        .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                ).exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
                .build();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
