package me.roybailey.springboot.common.product;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDto {

    private String productId;
    private String productName;

    private BigDecimal unitPrice;
    private String quantityPerUnit;
    private Long unitsInStock;
    private Long unitsOnOrder;
    private Long reorderLevel;
    private Boolean discontinued;

    private Long supplierId;
    private Long categoryId;
}
