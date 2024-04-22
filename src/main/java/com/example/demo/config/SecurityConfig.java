package com.example.demo.config;

import com.example.demo.model.ERole;
import com.example.demo.security.AuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationTokenFilter authenticationTokenFilter;

    public SecurityConfig(AuthenticationProvider authenticationProvider, AuthenticationTokenFilter authenticationTokenFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationTokenFilter = authenticationTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/auth/**",
                        "/static/**",
                        "/uploads/**",
                        "webhook/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                )
                .permitAll()

                .requestMatchers(HttpMethod.GET, "/address/address/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/address/address").hasRole("STAFF")
                .requestMatchers(HttpMethod.PUT, "/address/address/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.DELETE, "/address/address/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/user").hasRole("STAFF")
                .requestMatchers(HttpMethod.POST, "/user").hasRole("STAFF")
                .requestMatchers(HttpMethod.PUT, "/user").hasRole("STAFF")
                .requestMatchers(HttpMethod.DELETE, "/user").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/brand/brand/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/brand/brand").hasRole("STAFF")
                .requestMatchers(HttpMethod.PUT, "/brand/brand/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.DELETE, "/brand/brand/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/categories/category/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/categories/category").hasRole("STAFF")
                .requestMatchers(HttpMethod.PUT, "/categories/category/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.DELETE, "/categories/category/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/product/product/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/product/product").hasRole("STAFF")
                .requestMatchers(HttpMethod.PUT, "/product/product/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.DELETE, "/product/product/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/review/review/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/review/review").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.PUT, "/review/review/**").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.DELETE, "/review/review/**").hasRole("CUSTOMER")

                .requestMatchers(HttpMethod.GET, "/customer/customer/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.POST, "/customer/customer").permitAll()
                .requestMatchers(HttpMethod.PUT, "/customer/customer/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.DELETE, "/customer/customer/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/staff/staff/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.POST, "/staff/staff").hasRole("STAFF")
                .requestMatchers(HttpMethod.PUT, "/staff/staff/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.DELETE, "/staff/staff/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/orders/orders/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.POST, "/orders/orders").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.PUT, "/orders/orders/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.DELETE, "/orders/orders/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/payment/payment/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.POST, "/payment/payment").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.PUT, "/payment/payment/**").hasRole("STAFF")
                .requestMatchers(HttpMethod.DELETE, "/payment/payment/**").hasRole("STAFF")

                .requestMatchers(HttpMethod.GET, "/stripe/payment/**").hasRole("STAFF")
                
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .logout().disable()
        ;

        return http.build();
    }


}
