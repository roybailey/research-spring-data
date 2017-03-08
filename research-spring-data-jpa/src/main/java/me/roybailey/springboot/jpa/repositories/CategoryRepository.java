package me.roybailey.springboot.jpa.repositories;

import me.roybailey.springboot.jpa.domain.Category;
import me.roybailey.springboot.jpa.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long>{

    Category findByExternalId(String categoryId);
}
