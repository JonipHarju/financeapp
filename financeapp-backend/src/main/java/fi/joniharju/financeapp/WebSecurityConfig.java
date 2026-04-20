package fi.joniharju.financeapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Configures Spring Security for a REST API with a separate React frontend.
// Unlike the bookstore project which used Thymeleaf,
// this app disables form login and CSRF (no need for REST API) and instead
// exposes custom /api/auth endpoints for register, login and logout.
// CORS is enabled so the React frontend can communicate with the backend.
// All requests except /api/auth/** require the user to be authenticated.
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/api/auth/login")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .permitAll());
        return http.build();
    }
}
