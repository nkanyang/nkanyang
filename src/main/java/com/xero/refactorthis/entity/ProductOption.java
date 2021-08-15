package com.xero.refactorthis.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@Table(name = "ProductOptions")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(columnDefinition = "text", length = 128)
    private String name;

    @Column(columnDefinition = "text", length = 1024)
    private String description;
}
