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
public class Supplier {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    private String externalId;
    private String companyName;
    private String homepage;

    private String contact;
    private String address;
    private String phone;
    private String fax;

}
