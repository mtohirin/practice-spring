package com.gentara.practice.springboot.order.model;

import com.gentara.practice.springboot.customer.model.CustomerEntity;
import com.gentara.practice.springboot.product.model.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_order")
public class OrderEntity {
    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "order_number", unique = true, nullable = false, length = 50)
    private String orderNumber;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING"; // PENDING, CONFIRMED, PROCESSING, COMPLETED, CANCELLED

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    public OrderEntity(String id, String orderNumber, LocalDateTime orderDate, int quantity,
                       double unitPrice, double totalPrice, String status, String notes) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.status = status;
        this.notes = notes;
    }
}
