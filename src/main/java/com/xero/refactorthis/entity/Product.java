package com.xero.refactorthis.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Type(type="uuid-char")
    private UUID id;

    @Column(columnDefinition = "text", length = 128)
    private String name;

    @Column(columnDefinition = "text", length = 1024)
    private String description;

    private float price;

    private float deliveryPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<ProductOption> options = new ArrayList<>();
}
