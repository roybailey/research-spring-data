package me.roybailey.springboot.jpa.repositories;

import me.roybailey.springboot.jpa.domain.Product;
import me.roybailey.springboot.jpa.domain.Supplier;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Supplier findByExternalId(String productId);
}
