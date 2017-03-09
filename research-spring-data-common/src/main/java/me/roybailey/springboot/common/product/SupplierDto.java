package me.roybailey.springboot.common.product;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SupplierDto {

    private String supplierId;
    private String companyName;
    private String homepage;

    private String contact;
    private String address;
    private String phone;
    private String fax;
}
