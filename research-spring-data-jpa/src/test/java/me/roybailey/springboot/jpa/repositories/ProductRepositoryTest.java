package me.roybailey.springboot.jpa.repositories;

import lombok.extern.slf4j.Slf4j;
import me.roybailey.springboot.jpa.bootstrap.JpaProductLoader;
import me.roybailey.springboot.jpa.configuration.JpaRepositoryConfiguration;
import me.roybailey.springboot.jpa.domain.Category;
import me.roybailey.springboot.jpa.domain.Product;
import me.roybailey.springboot.jpa.domain.Supplier;
import me.roybailey.springboot.jpa.services.ProductService;
import org.assertj.core.api.JUnitBDDSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JpaRepositoryConfiguration.class, JpaProductLoader.class})
public class ProductRepositoryTest {

    @Autowired
    private ProductService productService;

    @Rule
    public TestName name= new TestName();

    @Rule
    public final JUnitBDDSoftAssertions softly = new JUnitBDDSoftAssertions();

    @Test
    public void testLoadedProducts(){

        List<Category> allCategories = productService.listAllCategories();
        log.info(name+" loaded Category data = " + allCategories);
        softly.then(allCategories).isNotNull().hasSize(8);

        List<Supplier> allSuppliers = productService.listAllSuppliers();
        log.info(name+" loaded Supplier data = " + allSuppliers);
        softly.then(allSuppliers).isNotNull().hasSize(29);

        List<Product> allProducts = productService.listAllProducts();
        log.info(name+" loaded Product data = " + allProducts);
        softly.then(allProducts).isNotNull().hasSize(77);
    }

}
