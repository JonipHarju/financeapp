package fi.joniharju.financeapp.repository;

import org.springframework.data.repository.CrudRepository;

import fi.joniharju.financeapp.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
