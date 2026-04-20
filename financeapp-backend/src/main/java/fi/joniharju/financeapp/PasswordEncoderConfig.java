package fi.joniharju.financeapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    // Separated from WebSecurityConfig so that @DataJpaTest slices can import it
    // without loading the full security configuration. Without doing this the
    // repository and service tests will always all just fail
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
