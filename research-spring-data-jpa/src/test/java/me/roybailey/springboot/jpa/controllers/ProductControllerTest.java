package me.roybailey.springboot.jpa.controllers;


import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import me.roybailey.springboot.jpa.SpringBootJpaApplication;
import me.roybailey.springboot.jpa.domain.Category;
import me.roybailey.springboot.jpa.domain.Product;
import me.roybailey.springboot.jpa.domain.Supplier;
import org.assertj.core.api.JUnitBDDSoftAssertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = SpringBootJpaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    int port;

    @Rule
    public TestName name= new TestName();

    @Rule
    public final JUnitBDDSoftAssertions softly = new JUnitBDDSoftAssertions();

    @Headers("Accept: application/json")
    public interface ProductApi {

        @RequestLine("GET /category")
        List<Category> getAllCategories();

        @RequestLine("GET /supplier")
        List<Supplier> getAllSuppliers();

        @RequestLine("GET /product")
        List<Product> getAllProducts();

        @RequestLine("GET /product/{id}")
        Product getProduct(@Param("id") Long id);

        @Headers("Content-Type: application/json")
        @RequestLine("POST /product")
        Product saveProduct(Product newProduct);

        @RequestLine("DELETE /product/{id}")
        void deleteProduct(@Param("id") Long id);
    }

    private ProductApi api;

    @Before
    public void apiSetup() {
        this.api = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logLevel(Logger.Level.BASIC)
                .target(ProductApi.class, "http://localhost:"+port);
    }

    @Test
    public void test1_CategoryApi() {

        List<Category> allCategories = api.getAllCategories();
        softly.then(allCategories).isNotNull();
        softly.then(allCategories).hasSize(8);
    }

    @Test
    public void test2_SupplierApi() {

        List<Supplier> allSuppliers = api.getAllSuppliers();
        softly.then(allSuppliers).isNotNull();
        softly.then(allSuppliers).hasSize(29);
    }

    @Test
    public void test3_ProductApi() {

        List<Product> allProducts = api.getAllProducts();
        softly.then(allProducts).isNotNull();
        softly.then(allProducts).hasSize(77);

        Product expected = allProducts.get(10);
        Product actual = api.getProduct(expected.getId());
        softly.then(actual).isNotNull();
        softly.then(actual).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void test4_ProductApiUpdates() {

        List<Product> allProducts = api.getAllProducts();
        softly.then(allProducts).isNotNull();
        softly.then(allProducts).hasSize(77);

        Product newProduct = Product.builder()
                .productName("extra")
                .unitPrice(BigDecimal.TEN)
                .unitsInStock(10L)
                .unitsOnOrder(10L)
                .reorderLevel(10L)
                .build();
        Product savedProduct = api.saveProduct(newProduct);
        softly.then(savedProduct).isNotNull();
        softly.then(savedProduct.getId()).isNotNull();
        softly.then(savedProduct).isEqualToIgnoringNullFields(newProduct);

        allProducts = api.getAllProducts();
        softly.then(allProducts).isNotNull();
        softly.then(allProducts).hasSize(78);

        api.deleteProduct(savedProduct.getId());

        allProducts = api.getAllProducts();
        softly.then(allProducts).isNotNull();
        softly.then(allProducts).hasSize(77);
    }
}
