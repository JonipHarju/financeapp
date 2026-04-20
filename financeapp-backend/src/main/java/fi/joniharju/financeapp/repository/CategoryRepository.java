package fi.joniharju.financeapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findAllByUser(AppUser user);

}
