package fi.joniharju.financeapp.repository;

import org.springframework.data.repository.CrudRepository;

import fi.joniharju.financeapp.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
