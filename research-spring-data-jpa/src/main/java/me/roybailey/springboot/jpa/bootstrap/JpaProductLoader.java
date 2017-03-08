package me.roybailey.springboot.jpa.bootstrap;

import lombok.extern.slf4j.Slf4j;
import me.roybailey.springboot.common.product.ProductLoaderUtil;
import me.roybailey.springboot.jpa.domain.Category;
import me.roybailey.springboot.jpa.domain.Product;
import me.roybailey.springboot.jpa.domain.Supplier;
import me.roybailey.springboot.jpa.repositories.CategoryRepository;
import me.roybailey.springboot.jpa.repositories.ProductRepository;
import me.roybailey.springboot.jpa.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Slf4j
@Service
public class JpaProductLoader {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    private static AtomicBoolean loaded = new AtomicBoolean();

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        synchronized (loaded) {
            if(!loaded.get()) {
                loadProductsFromCsv();
                loaded.set(true);
            }
        }
    }

    private void loadProductsFromCsv() {
        log.info("Loading Products into ProductRepository: " + productRepository);

        try {
            // categoryId,categoryName,description,picture
            List<Category> allCategories = ProductLoaderUtil.loadCategories((record) -> Category.builder()
                    .externalId(record.get("categoryId"))
                    .categoryName(record.get("categoryName"))
                    .description(record.get("description"))
                    .build());
            categoryRepository.save(allCategories);
        } catch (IOException err) {
            log.error("Failed to load Products", err);
        }

        try {
            // supplierID,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax,homePage
            List<Supplier> allSuppliers = ProductLoaderUtil.loadSuppliers((record) -> Supplier.builder()
                    .externalId(record.get("supplierID"))
                    .companyName(record.get("companyName"))
                    .contact(record.get("contactName"))
                    .address(record.get("address"))
                    .phone(record.get("phone"))
                    .fax(record.get("fax"))
                    .homepage(record.get("homePage"))
                    .build());
            supplierRepository.save(allSuppliers);
        } catch (IOException err) {
            log.error("Failed to load Products", err);
        }

        try {
            // productID,productName,supplierID,categoryId,quantityPerUnit,unitPrice,unitsInStock,unitsOnOrder,reorderLevel,discontinued
            List<Product> allProducts = ProductLoaderUtil.loadProducts((record) -> Product.builder()
                    .externalId(record.get("productID"))
                    .productName(record.get("productName"))
                    .quantityPerUnit(record.get("quantityPerUnit"))
                    .unitPrice(new BigDecimal(record.get("unitPrice")))
                    .unitsInStock(Long.parseLong(record.get("unitsInStock")))
                    .unitsOnOrder(Long.parseLong(record.get("unitsOnOrder")))
                    .reorderLevel(Long.parseLong(record.get("reorderLevel")))
                    .discontinued(Integer.parseInt(record.get("discontinued")) == 1)
                    .supplier(supplierRepository.findByExternalId(record.get("supplierID")))
                    .category(categoryRepository.findByExternalId(record.get("categoryId")))
                    .build());
            productRepository.save(allProducts);
        } catch (IOException err) {
            log.error("Failed to load Products", err);
        }
    }
}
