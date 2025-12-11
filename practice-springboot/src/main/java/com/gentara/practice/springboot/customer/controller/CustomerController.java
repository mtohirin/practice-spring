package com.gentara.practice.springboot.customer.controller;

import com.gentara.practice.springboot.customer.model.CustomerModel;
import com.gentara.practice.springboot.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET ALL CUSTOMERS
    @GetMapping
    public ResponseEntity<List<CustomerModel>> getAllCustomers() {
        try {
            List<CustomerModel> customers = customerService.getAll();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET CUSTOMER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable String id) {
        try {
            CustomerModel customer = customerService.getById(id);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET CUSTOMER BY EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerModel> getCustomerByEmail(@PathVariable String email) {
        try {
            CustomerModel customer = customerService.getByEmail(email);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE NEW CUSTOMER
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerModel request) {
        try {
            CustomerModel createdCustomer = customerService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create customer"));
        }
    }

    // UPDATE CUSTOMER
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @RequestBody CustomerModel request) {
        try {
            CustomerModel updatedCustomer = customerService.update(id, request);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update customer"));
        }
    }

    // DELETE CUSTOMER
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        try {
            customerService.delete(id);
            return ResponseEntity.ok().body(Map.of("message", "Customer deleted successfully"));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete customer"));
        }
    }

    // GET ACTIVE CUSTOMERS
    @GetMapping("/active")
    public ResponseEntity<List<CustomerModel>> getActiveCustomers() {
        try {
            List<CustomerModel> customers = customerService.getActiveCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH CUSTOMERS BY NAME
    @GetMapping("/search/name")
    public ResponseEntity<List<CustomerModel>> searchByName(@RequestParam String name) {
        try {
            List<CustomerModel> customers = customerService.findByName(name);
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH CUSTOMERS BY EMAIL
    @GetMapping("/search/email")
    public ResponseEntity<List<CustomerModel>> searchByEmail(@RequestParam String email) {
        try {
            List<CustomerModel> customers = customerService.findByEmail(email);
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH CUSTOMERS BY NAME AND EMAIL
    @GetMapping("/search/name-email")
    public ResponseEntity<List<CustomerModel>> searchByNameAndEmail(
            @RequestParam String name,
            @RequestParam String email) {
        try {
            List<CustomerModel> customers = customerService.findByNameAndEmail(name, email);
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH CUSTOMERS BY PHONE
    @GetMapping("/search/phone")
    public ResponseEntity<List<CustomerModel>> searchByPhone(@RequestParam String phone) {
        try {
            List<CustomerModel> customers = customerService.findByPhone(phone);
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ACTIVATE CUSTOMER
    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateCustomer(@PathVariable String id) {
        try {
            CustomerModel updatedCustomer = customerService.activateCustomer(id);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to activate customer"));
        }
    }

    // DEACTIVATE CUSTOMER
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateCustomer(@PathVariable String id) {
        try {
            CustomerModel updatedCustomer = customerService.deactivateCustomer(id);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to deactivate customer"));
        }
    }

    // CHECK IF EMAIL EXISTS
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmailExists(@RequestParam String email) {
        try {
            boolean exists = customerService.existsByEmail(email);
            return ResponseEntity.ok(Map.of("exists", exists));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to check email existence"));
        }
    }

    // GET ACTIVE CUSTOMER COUNT
    @GetMapping("/count/active")
    public ResponseEntity<?> getActiveCustomerCount() {
        try {
            long count = customerService.getActiveCustomerCount();
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get active customer count"));
        }
    }
}