package com.ecommerce.ecommerce_remake.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ecommerce.ecommerce_remake.feature.user.enums.Role.SELLER;
import static com.ecommerce.ecommerce_remake.feature.user.enums.Role.USER;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                                authorize
                                        .requestMatchers("/api/auth/**").permitAll()
                                        .requestMatchers("/api/image/**").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/api/product/**").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/api/factory/{id}/product").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/api/factory/{id}/store").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/api/store/{storeId}/store-metrics").permitAll()
                                        .requestMatchers(HttpMethod.POST,"/api/product/save").hasAuthority(SELLER.name())
                                        .requestMatchers(HttpMethod.DELETE,"/api/cart/**").hasAnyAuthority(SELLER.name(), USER.name())
                                        .anyRequest()
                                        .authenticated());
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authenticationProvider(authenticationProvider);
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
