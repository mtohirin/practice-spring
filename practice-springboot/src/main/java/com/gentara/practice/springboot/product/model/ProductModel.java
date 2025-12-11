package com.gentara.practice.springboot.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private String id;
    private String brand;
    private String type;
    private String flavor;
    private double price;
    private int stock;
    private String description;
    private boolean isActive;
}