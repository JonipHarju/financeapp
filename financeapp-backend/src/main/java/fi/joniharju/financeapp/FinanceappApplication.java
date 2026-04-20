package fi.joniharju.financeapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.repository.AppUserRepository;

@SpringBootApplication
public class FinanceappApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceappApplication.class, args);
	}

	// creating a test user for demo purposes here.
	@Bean
	public CommandLineRunner demoData(AppUserRepository appUserRepository, BCryptPasswordEncoder passwordEncoder) {
		return (args) -> {
			if (appUserRepository.findByUsername("demo").isEmpty()) {
				appUserRepository.save(new AppUser("demo", passwordEncoder.encode("demo123"), "demo@financeapp.com"));
			}
		};
	}

}
