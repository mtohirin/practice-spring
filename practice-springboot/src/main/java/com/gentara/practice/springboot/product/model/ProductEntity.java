package com.gentara.practice.springboot.product.model;

import com.gentara.practice.springboot.order.model.OrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_product")
public class ProductEntity {
    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "flavor", length = 100)
    private String flavor;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "stock", nullable = false)
    private int stock = 0;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderEntity> orders = new ArrayList<>();

    public ProductEntity(String id, String brand, String type, String flavor, double price, int stock, String description) {
        this.id = id;
        this.brand = brand;
        this.type = type;
        this.flavor = flavor;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.isActive = true;
        this.orders = new ArrayList<>();
    }
}

