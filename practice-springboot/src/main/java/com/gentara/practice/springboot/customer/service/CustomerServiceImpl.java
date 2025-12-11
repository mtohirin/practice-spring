package com.gentara.practice.springboot.customer.service;

import com.gentara.practice.springboot.customer.model.CustomerEntity;
import com.gentara.practice.springboot.customer.model.CustomerModel;
import com.gentara.practice.springboot.customer.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerModel> getAll() {
        try {
            log.info("Fetching all customers");
            List<CustomerEntity> result = customerRepo.findAll();
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting all customers: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve customers: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerModel getById(String id) {
        log.info("Fetching customer by id: {}", id);
        CustomerEntity entity = customerRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer not found with id: {}", id);
                    return new RuntimeException("Customer not found with id: " + id);
                });
        return convertToModel(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerModel getByEmail(String email) {
        log.info("Fetching customer by email: {}", email);
        CustomerEntity entity = customerRepo.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Customer not found with email: {}", email);
                    return new RuntimeException("Customer not found with email: " + email);
                });
        return convertToModel(entity);
    }

    @Override
    public CustomerModel create(CustomerModel request) {
        log.info("Creating new customer: {} ({})", request.getName(), request.getEmail());
        validateCustomerRequest(request);

        // Check if email already exists
        if (customerRepo.existsByEmail(request.getEmail())) {
            log.warn("Customer with email {} already exists", request.getEmail());
            throw new RuntimeException("Customer with email " + request.getEmail() + " already exists");
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(request.getName().trim());
        entity.setEmail(request.getEmail().trim().toLowerCase());
        entity.setPhone(request.getPhone() != null ? request.getPhone().trim() : null);
        entity.setAddress(request.getAddress());
        entity.setRegistrationDate(LocalDateTime.now());
        entity.setActive(true);

        try {
            CustomerEntity savedEntity = customerRepo.save(entity);
            log.info("Customer created successfully with id: {}", savedEntity.getId());
            return convertToModel(savedEntity);
        } catch (Exception e) {
            log.error("Failed to create customer: {}", e.getMessage());
            throw new RuntimeException("Failed to create customer: " + e.getMessage());
        }
    }

    @Override
    public CustomerModel update(String id, CustomerModel request) {
        log.info("Updating customer with id: {}", id);
        validateCustomerRequest(request);

        CustomerEntity entity = customerRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer not found for update with id: {}", id);
                    return new RuntimeException("Customer not found with id: " + id);
                });

        // Check if email is being changed and if new email already exists
        if (!entity.getEmail().equalsIgnoreCase(request.getEmail())) {
            if (customerRepo.existsByEmail(request.getEmail())) {
                log.warn("Email {} already exists for another customer", request.getEmail());
                throw new RuntimeException("Email " + request.getEmail() + " already exists for another customer");
            }
        }

        entity.setName(request.getName().trim());
        entity.setEmail(request.getEmail().trim().toLowerCase());
        entity.setPhone(request.getPhone() != null ? request.getPhone().trim() : null);
        entity.setAddress(request.getAddress());

        try {
            CustomerEntity updatedEntity = customerRepo.save(entity);
            log.info("Customer updated successfully with id: {}", id);
            return convertToModel(updatedEntity);
        } catch (Exception e) {
            log.error("Failed to update customer with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update customer: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        log.info("Deleting customer with id: {}", id);
        CustomerEntity entity = customerRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer not found for deletion with id: {}", id);
                    return new RuntimeException("Customer not found with id: " + id);
                });

        try {
            customerRepo.delete(entity);
            log.info("Customer deleted successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Failed to delete customer with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete customer: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerModel> getActiveCustomers() {
        try {
            log.info("Fetching active customers");
            List<CustomerEntity> result = customerRepo.findByIsActiveTrue();
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting active customers: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve active customers: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerModel> findByName(String name) {
        try {
            log.info("Finding customers by name: {}", name);
            List<CustomerEntity> result = customerRepo.findByNameContainingIgnoreCase(name);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding customers by name {}: {}", name, e.getMessage());
            throw new RuntimeException("Failed to find customers by name: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerModel> findByEmail(String email) {
        try {
            log.info("Finding customers by email: {}", email);
            List<CustomerEntity> result = customerRepo.findByEmailContaining(email);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding customers by email {}: {}", email, e.getMessage());
            throw new RuntimeException("Failed to find customers by email: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerModel> findByNameAndEmail(String name, String email) {
        try {
            log.info("Finding customers by name: {} and email: {}", name, email);
            List<CustomerEntity> result = customerRepo.findByNameAndEmail(name, email);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding customers by name {} and email {}: {}", name, email, e.getMessage());
            throw new RuntimeException("Failed to find customers by name and email: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerModel> findByPhone(String phone) {
        try {
            log.info("Finding customers by phone: {}", phone);
            List<CustomerEntity> result = customerRepo.findByPhoneContaining(phone);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding customers by phone {}: {}", phone, e.getMessage());
            throw new RuntimeException("Failed to find customers by phone: " + e.getMessage());
        }
    }

    @Override
    public CustomerModel activateCustomer(String id) {
        log.info("Activating customer with id: {}", id);
        return setCustomerActiveStatus(id, true);
    }

    @Override
    public CustomerModel deactivateCustomer(String id) {
        log.info("Deactivating customer with id: {}", id);
        return setCustomerActiveStatus(id, false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        try {
            return customerRepo.existsByEmail(email);
        } catch (Exception e) {
            log.error("Error checking email existence {}: {}", email, e.getMessage());
            throw new RuntimeException("Failed to check email existence: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long getActiveCustomerCount() {
        try {
            return customerRepo.countByIsActiveTrue();
        } catch (Exception e) {
            log.error("Error counting active customers: {}", e.getMessage());
            throw new RuntimeException("Failed to count active customers: " + e.getMessage());
        }
    }

    private CustomerModel convertToModel(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CustomerModel(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getRegistrationDate(),
                entity.isActive()
        );
    }

    private CustomerModel setCustomerActiveStatus(String id, boolean isActive) {
        CustomerEntity entity = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        entity.setActive(isActive);

        try {
            CustomerEntity updatedEntity = customerRepo.save(entity);
            log.info("Customer {} successfully with id: {}", isActive ? "activated" : "deactivated", id);
            return convertToModel(updatedEntity);
        } catch (Exception e) {
            log.error("Failed to {} customer with id {}: {}", isActive ? "activate" : "deactivate", id, e.getMessage());
            throw new RuntimeException("Failed to " + (isActive ? "activate" : "deactivate") + " customer: " + e.getMessage());
        }
    }

    private void validateCustomerRequest(CustomerModel request) {
        if (request == null) {
            throw new IllegalArgumentException("Customer request cannot be null");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        // Basic email validation
        if (!isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private boolean isValidEmail(String email) {
        // Simple email validation regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }
}
