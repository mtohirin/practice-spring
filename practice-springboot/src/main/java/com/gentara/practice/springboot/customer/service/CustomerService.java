package com.gentara.practice.springboot.customer.service;

import com.gentara.practice.springboot.customer.model.CustomerModel;

import java.util.List;

public interface CustomerService {
    // Basic CRUD operations
    List<CustomerModel> getAll();
    CustomerModel getById(String id);
    CustomerModel getByEmail(String email);
    CustomerModel create(CustomerModel request);
    CustomerModel update(String id, CustomerModel request);
    void delete(String id);

    // Additional business operations
    List<CustomerModel> getActiveCustomers();
    List<CustomerModel> findByName(String name);
    List<CustomerModel> findByEmail(String email);
    List<CustomerModel> findByNameAndEmail(String name, String email);
    List<CustomerModel> findByPhone(String phone);
    CustomerModel activateCustomer(String id);
    CustomerModel deactivateCustomer(String id);
    boolean existsByEmail(String email);
    long getActiveCustomerCount();
}
