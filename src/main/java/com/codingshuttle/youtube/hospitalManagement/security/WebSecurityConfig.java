package com.codingshuttle.youtube.hospitalManagement.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private  final PasswordEncoder passwordEncoder;
    private  final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(  sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/public/**","/auth/**").permitAll()
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/doctors/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterBefore( jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth-> oAuth.failureHandler(
                        ((request, response, exception) -> {
                            log.error("OAuth Error:{}", exception.getMessage());
                        })
                ));

          //      .formLogin();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    UserDetailsService  userDetailsService() {
//        UserDetails user1= User.withUsername("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("ADMIN").build();
//
//        UserDetails user2 = User.withUsername("patient")
//                .password(passwordEncoder.encode("patient"))
//                .roles("PATIENT").build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }

}
