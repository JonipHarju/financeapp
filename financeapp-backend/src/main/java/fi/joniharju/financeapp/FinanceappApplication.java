package fi.joniharju.financeapp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.Category;
import fi.joniharju.financeapp.entity.Transaction;
import fi.joniharju.financeapp.entity.TransactionType;
import fi.joniharju.financeapp.repository.AppUserRepository;
import fi.joniharju.financeapp.repository.CategoryRepository;
import fi.joniharju.financeapp.repository.TransactionRepository;

@SpringBootApplication
public class FinanceappApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceappApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(AppUserRepository appUserRepository, CategoryRepository categoryRepository,
			TransactionRepository transactionRepository, BCryptPasswordEncoder passwordEncoder) {
		return (args) -> {
			if (appUserRepository.findByUsername("demo").isPresent()) {
				return;
			}

			AppUser demo = appUserRepository.save(
					new AppUser("demo", passwordEncoder.encode("demo123"), "demo@financeapp.com"));

			Category salary = categoryRepository.save(new Category("Salary", demo, null));
			Category freelance = categoryRepository.save(new Category("Freelance", demo, null));
			Category rent = categoryRepository.save(new Category("Rent", demo, null));
			Category groceries = categoryRepository.save(new Category("Groceries", demo, null));
			Category transport = categoryRepository.save(new Category("Transport", demo, null));
			Category entertainment = categoryRepository.save(new Category("Entertainment", demo, null));
			Category utilities = categoryRepository.save(new Category("Utilities", demo, null));
			Category health = categoryRepository.save(new Category("Health", demo, null));

			LocalDate today = LocalDate.now();

			transactionRepository.saveAll(List.of(
					new Transaction(new BigDecimal("3200.00"), today.minusMonths(2).withDayOfMonth(1), "Monthly salary",
							TransactionType.INCOME, demo, salary),
					new Transaction(new BigDecimal("3200.00"), today.minusMonths(1).withDayOfMonth(1), "Monthly salary",
							TransactionType.INCOME, demo, salary),
					new Transaction(new BigDecimal("3200.00"), today.withDayOfMonth(1), "Monthly salary",
							TransactionType.INCOME, demo, salary),
					new Transaction(new BigDecimal("850.00"), today.minusMonths(2).withDayOfMonth(5), "Website project",
							TransactionType.INCOME, demo, freelance),
					new Transaction(new BigDecimal("1200.00"), today.minusMonths(1).withDayOfMonth(12),
							"Mobile app contract", TransactionType.INCOME, demo, freelance),

					new Transaction(new BigDecimal("950.00"), today.minusMonths(2).withDayOfMonth(3), "Rent",
							TransactionType.EXPENSE, demo, rent),
					new Transaction(new BigDecimal("950.00"), today.minusMonths(1).withDayOfMonth(3), "Rent",
							TransactionType.EXPENSE, demo, rent),
					new Transaction(new BigDecimal("950.00"), today.withDayOfMonth(3), "Rent", TransactionType.EXPENSE,
							demo, rent),

					new Transaction(new BigDecimal("87.50"), today.minusWeeks(8), "Weekly groceries",
							TransactionType.EXPENSE, demo, groceries),
					new Transaction(new BigDecimal("92.30"), today.minusWeeks(7), "Weekly groceries",
							TransactionType.EXPENSE, demo, groceries),
					new Transaction(new BigDecimal("78.90"), today.minusWeeks(6), "Weekly groceries",
							TransactionType.EXPENSE, demo, groceries),
					new Transaction(new BigDecimal("105.20"), today.minusWeeks(5), "Weekly groceries",
							TransactionType.EXPENSE, demo, groceries),
					new Transaction(new BigDecimal("88.60"), today.minusWeeks(4), "Weekly groceries",
							TransactionType.EXPENSE, demo, groceries),
					new Transaction(new BigDecimal("94.10"), today.minusWeeks(3), "Weekly groceries",
							TransactionType.EXPENSE, demo, groceries),
					new Transaction(new BigDecimal("81.40"), today.minusWeeks(2), "Weekly groceries",
							TransactionType.EXPENSE, demo, groceries),
					new Transaction(new BigDecimal("99.70"), today.minusWeeks(1), "Weekly groceries",
							TransactionType.EXPENSE, demo, groceries),

					new Transaction(new BigDecimal("55.00"), today.minusMonths(2).withDayOfMonth(10),
							"Monthly bus pass", TransactionType.EXPENSE, demo, transport),
					new Transaction(new BigDecimal("55.00"), today.minusMonths(1).withDayOfMonth(10),
							"Monthly bus pass", TransactionType.EXPENSE, demo, transport),
					new Transaction(new BigDecimal("23.50"), today.minusDays(5), "Taxi", TransactionType.EXPENSE, demo,
							transport),

					new Transaction(new BigDecimal("15.99"), today.minusMonths(2).withDayOfMonth(15), "Netflix",
							TransactionType.EXPENSE, demo, entertainment),
					new Transaction(new BigDecimal("15.99"), today.minusMonths(1).withDayOfMonth(15), "Netflix",
							TransactionType.EXPENSE, demo, entertainment),
					new Transaction(new BigDecimal("42.00"), today.minusMonths(1).withDayOfMonth(20), "Concert tickets",
							TransactionType.EXPENSE, demo, entertainment),
					new Transaction(new BigDecimal("15.99"), today.withDayOfMonth(15), "Netflix",
							TransactionType.EXPENSE, demo, entertainment),

					new Transaction(new BigDecimal("120.00"), today.minusMonths(2).withDayOfMonth(7),
							"Electricity & water", TransactionType.EXPENSE, demo, utilities),
					new Transaction(new BigDecimal("115.50"), today.minusMonths(1).withDayOfMonth(7),
							"Electricity & water", TransactionType.EXPENSE, demo, utilities),

					new Transaction(new BigDecimal("35.00"), today.minusMonths(1).withDayOfMonth(18), "Pharmacy",
							TransactionType.EXPENSE, demo, health),
					new Transaction(new BigDecimal("80.00"), today.minusDays(10), "Doctor visit",
							TransactionType.EXPENSE, demo, health)));
		};
	}

}
