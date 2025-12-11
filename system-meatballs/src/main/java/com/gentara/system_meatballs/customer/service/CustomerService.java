package com.gentara.system_meatballs.customer.service;

import com.gentara.system_meatballs.customer.model.CustomerModel;

import java.util.List;

public interface CustomerService {
    List<CustomerModel>getAll();
    CustomerModel getById(String id);
    CustomerModel save(CustomerModel request);
    CustomerModel update(String id, CustomerModel request);
    CustomerModel delete(String id);
}
