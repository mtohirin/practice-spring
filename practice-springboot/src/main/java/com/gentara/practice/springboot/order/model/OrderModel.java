package com.gentara.practice.springboot.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private String id;
    private String orderNumber;
    private LocalDateTime orderDate;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private String status;
    private String notes;

    // Customer info
    private String customerId;
    private String customerName;
    private String customerEmail;

    // Product info
    private String productId;
    private String productName;
    private String productBrand;
    private String productType;
}