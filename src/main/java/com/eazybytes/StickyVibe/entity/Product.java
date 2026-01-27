package com.eazybytes.StickyVibe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.eazybytes.StickyVibe.entity.BaseEntity;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTS")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 250)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false, length = 500)
    private String description;

    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "POPULARITY", nullable = false)
    private Integer popularity;

    @Column(name = "IMAGE_URL", length = 500)
    private String imageUrl;

    // Audit fields are inherited from BaseEntity

}