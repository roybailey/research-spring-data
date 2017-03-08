package me.roybailey.springboot.jpa.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
@Table(name = "CATEGORY")
public class Category {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private Long id;

    @Version
    @Column(name="VERSION")
    private Long version;

    @Column(name="EXTERNAL_ID")
    private String externalId;

    @Column(name="CATEGORY_NAME")
    private String categoryName;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="PICTURE")
    private String picture;
}
