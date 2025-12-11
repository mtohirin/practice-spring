package com.gentara.system_meatballs.customer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {
    private String id;
    private String name;
    private String email;
    private String phone;
}
