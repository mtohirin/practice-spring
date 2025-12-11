package com.gentara.system_meatballs.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_order")
public class OrderEntity {
    @Id
    @Column
    private String id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "quantity")
    private int quantity;


}
