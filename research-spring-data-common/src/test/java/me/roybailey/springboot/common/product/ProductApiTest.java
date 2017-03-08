package me.roybailey.springboot.common.product;

import org.junit.Test;


public class ProductApiTest {

    @Test
    public void testClasspath() {
        System.out.println(getClass().getClassLoader().getResource("/northwind/products.csv"));
        System.out.println(ClassLoader.getSystemResourceAsStream("northwind/products.csv"));
    }

}
