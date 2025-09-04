package com.codearp.application.products.domains;

import com.codearp.application.commons.domains.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="PRODUCTS")
@Builder @NoArgsConstructor @AllArgsConstructor
@Setter @Getter @ToString @EqualsAndHashCode(callSuper = false)
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;

    @Lob
    private String description;

    @Column(unique = true,nullable = false)
    private String sku;

    @Version
    private Long version;

}
