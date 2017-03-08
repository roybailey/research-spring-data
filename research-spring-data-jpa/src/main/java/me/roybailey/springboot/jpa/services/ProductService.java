package me.roybailey.springboot.jpa.services;


import me.roybailey.springboot.jpa.domain.Category;
import me.roybailey.springboot.jpa.domain.Product;
import me.roybailey.springboot.jpa.domain.Supplier;

import java.util.List;

public interface ProductService {

    List<Category> listAllCategories();

    List<Supplier> listAllSuppliers();

    List<Product> listAllProducts();

    Product getProductById(Long id);

    Product saveProduct(Product product);

}
