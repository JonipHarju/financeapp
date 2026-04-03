package fi.joniharju.financeapp.repository;

import org.springframework.data.repository.CrudRepository;

import fi.joniharju.financeapp.entity.AppUser;

public interface UserRepository extends CrudRepository<AppUser, Long> {

}
