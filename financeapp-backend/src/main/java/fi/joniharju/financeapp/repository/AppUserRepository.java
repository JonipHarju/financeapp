package fi.joniharju.financeapp.repository;

import org.springframework.data.repository.CrudRepository;

import fi.joniharju.financeapp.entity.AppUser;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);
}
