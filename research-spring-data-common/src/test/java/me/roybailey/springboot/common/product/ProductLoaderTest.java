package me.roybailey.springboot.common.product;

import org.assertj.core.api.JUnitBDDSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.math.BigDecimal;
import java.util.List;


public class ProductLoaderTest {

    @Rule
    public TestName name= new TestName();

    @Rule
    public final JUnitBDDSoftAssertions softly = new JUnitBDDSoftAssertions();

    @Test
    public void testLoadingCategories() throws Exception {

        List<CategoryDto> allCategories = ProductLoaderUtil.loadCategories((record) -> CategoryDto.builder()
                .categoryId(record.get("categoryId"))
                .categoryName(record.get("categoryName"))
                .description(record.get("description"))
                .build());

        allCategories.forEach((product)->{
            softly.then(product.getCategoryId()).isNotNull();
            softly.then(product.getCategoryName()).isNotNull();
            softly.then(product.getDescription()).isNotNull();
        });
    }

    @Test
    public void testLoadingSuppliers() throws Exception {

        List<SupplierDto> allSuppliers = ProductLoaderUtil.loadSuppliers((record) -> SupplierDto.builder()
                .supplierId(record.get("supplierID"))
                .companyName(record.get("companyName"))
                .contact(record.get("contactName"))
                .address(record.get("address"))
                .phone(record.get("phone"))
                .fax(record.get("fax"))
                .homepage(record.get("homePage"))
                .build());

        allSuppliers.forEach((product)->{
            softly.then(product.getSupplierId()).isNotNull();
            softly.then(product.getCompanyName()).isNotNull();
            softly.then(product.getContact()).isNotNull();
            softly.then(product.getAddress()).isNotNull();
            softly.then(product.getPhone()).isNotNull();
            softly.then(product.getFax()).isNotNull();
            softly.then(product.getHomepage()).isNotNull();
        });
    }

    @Test
    public void testLoadingProducts() throws Exception {

        List<ProductDto> allProducts = ProductLoaderUtil.loadProducts((record) -> ProductDto.builder()
                .productId(record.get("productID"))
                .productName(record.get("productName"))
                .quantityPerUnit(record.get("quantityPerUnit"))
                .unitPrice(new BigDecimal(record.get("unitPrice")))
                .unitsInStock(Long.parseLong(record.get("unitsInStock")))
                .unitsOnOrder(Long.parseLong(record.get("unitsOnOrder")))
                .reorderLevel(Long.parseLong(record.get("reorderLevel")))
                .discontinued(Integer.parseInt(record.get("discontinued")) == 1)
                .supplierId(Long.parseLong(record.get("supplierID")))
                .categoryId(Long.parseLong(record.get("categoryId")))
                .build());

        allProducts.forEach((product)->{
            softly.then(product.getProductId()).isNotNull();
            softly.then(product.getProductName()).isNotNull();
            softly.then(product.getQuantityPerUnit()).isNotNull();
            softly.then(product.getUnitPrice()).isNotNull();
            softly.then(product.getUnitsInStock()).isNotNull();
            softly.then(product.getUnitsOnOrder()).isNotNull();
            softly.then(product.getReorderLevel()).isNotNull();
            softly.then(product.getDiscontinued()).isNotNull();
            softly.then(product.getSupplierId()).isNotNull();
            softly.then(product.getCategoryId()).isNotNull();
        });
    }

}
