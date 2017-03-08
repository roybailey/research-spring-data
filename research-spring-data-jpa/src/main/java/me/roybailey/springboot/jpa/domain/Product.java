package me.roybailey.springboot.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    private String externalId;
    private String productName;

    private BigDecimal unitPrice;
    private String quantityPerUnit;
    private Long unitsInStock;
    private Long unitsOnOrder;
    private Long reorderLevel;
    private Boolean discontinued;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private Category category;
}
