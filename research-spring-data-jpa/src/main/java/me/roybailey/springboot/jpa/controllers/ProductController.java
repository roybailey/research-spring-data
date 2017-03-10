package me.roybailey.springboot.jpa.controllers;

import lombok.extern.slf4j.Slf4j;
import me.roybailey.springboot.jpa.domain.Product;
import me.roybailey.springboot.jpa.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/category")
    public ResponseEntity<?> getCategories() {
        log.info("getCategories()");
        return ResponseEntity.ok(productService.listAllCategories());
    }

    @GetMapping(value = "/supplier")
    public ResponseEntity<?> getSupplier() {
        log.info("getSupplier()");
        return ResponseEntity.ok(productService.listAllSuppliers());
    }

    @GetMapping(value = "/product")
    public ResponseEntity<?> getProducts() {
        log.info("getProducts()");
        return ResponseEntity.ok(productService.listAllProducts());
    }

    @GetMapping("product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {

        log.info("getProduct({})", id);
        Product product = productService.getProductById(id);
        if (product != null)
            return ResponseEntity.ok(product);

        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "product")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {

        Product result = productService.saveProduct(product);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

}
