package me.roybailey.springboot.jpa.configuration;

import me.roybailey.springboot.jpa.services.ProductService;
import me.roybailey.springboot.jpa.services.ProductServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan(basePackages = {"me.roybailey.springboot.jpa.domain"})
@EnableJpaRepositories(basePackages = {"me.roybailey.springboot.jpa.repositories"})
public class JpaRepositoryConfiguration {

    @Bean
    ProductService productService() {
        return new ProductServiceImpl();
    }
}
