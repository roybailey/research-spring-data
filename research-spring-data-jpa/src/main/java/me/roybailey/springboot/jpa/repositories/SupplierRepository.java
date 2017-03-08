package me.roybailey.springboot.jpa.repositories;

import me.roybailey.springboot.jpa.domain.Supplier;
import org.springframework.data.repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    Supplier findByExternalId(String supplierId);
}
