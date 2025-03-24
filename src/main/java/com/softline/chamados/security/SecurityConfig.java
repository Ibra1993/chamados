package com.softline.chamados.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(); // Usando o BCryptPasswordEncoder

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Aplica o CORS corretamente
                .anonymous(anonymous -> anonymous.disable()) // Desativa autenticação anônima
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http))
                .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/usuario/auth/dadosUsuario").authenticated(); // Permite acesso apenas autenticado


                    // Configuração de rotas protegidas por papéis específicos
                    auth.requestMatchers("/components/adminSoftLine/**").hasAuthority("ADMIN");
                    auth.requestMatchers("/components/user/**").hasAuthority("USER");
                    auth.requestMatchers("/components/userSoftline/**").hasAuthority("USERSOFTLINE");
                    auth.requestMatchers("/components/implantacao/**").hasAuthority("IMPLANTACAO");
                    auth.requestMatchers("/components/customizacao/**").hasAuthority("CUSTOMIZACAO");


                    auth.anyRequest().permitAll();




                })

                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(customAuthenticationEntryPoint())
                )

                .build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

      CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();



        config.addAllowedOrigin("http://localhost:5173"); // Adicione aqui a origem do seu front-end
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");

        // ADICIONADO
        config.addAllowedMethod("OPTIONS");







        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        cors.registerCorsConfiguration("/**", config);

        return cors;

    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 org.springframework.security.core.AuthenticationException authException)
                    throws IOException, ServletException {

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Retorna status 403 Forbidden
                response.getWriter().write("{\"error\": \"Acesso não autorizado!\"}");

            }
        };
    }




}