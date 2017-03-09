package me.roybailey.springboot.common.product;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CategoryDto {

    private String categoryId;
    private String categoryName;
    private String description;
    private String picture;
}
