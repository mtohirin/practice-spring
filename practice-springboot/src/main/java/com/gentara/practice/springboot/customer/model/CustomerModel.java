package com.gentara.practice.springboot.customer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime registrationDate;
    private boolean isActive;
}

