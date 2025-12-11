package com.gentara.practice.springboot.config;

import com.gentara.practice.springboot.customer.model.CustomerEntity;
import com.gentara.practice.springboot.customer.repository.CustomerRepo;
import com.gentara.practice.springboot.order.model.OrderEntity;
import com.gentara.practice.springboot.order.repository.OrderRepo;
import com.gentara.practice.springboot.product.model.ProductEntity;
import com.gentara.practice.springboot.product.repository.ProductRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final ProductRepo productRepo;
    private final CustomerRepo customerRepo;
    private final OrderRepo orderRepo;

    @Override
    public void run(String... args) throws Exception {
        initTables();
    }

    private void initTables() {
        // Check if data already exists to avoid duplicates
        if (productRepo.count() > 0) {
            log.info("Data already initialized, skipping...");
            return;
        }

        log.info("Starting database initialization...");

        // Initialize Products
        ProductEntity blueBottle = new ProductEntity(
                UUID.randomUUID().toString(),
                "Blue Bottle",
                "Coffee",
                "Ethiopian Yirgacheffe",
                15.0,
                100,
                "Premium coffee with floral and citrus notes"
        );

        ProductEntity lavazza = new ProductEntity(
                UUID.randomUUID().toString(),
                "Lavazza",
                "Coffee",
                "Arabica Supremo",
                20.0,
                50,
                "Smooth chocolate and nutty flavor"
        );

        ProductEntity starbucks = new ProductEntity(
                UUID.randomUUID().toString(),
                "Starbucks",
                "Coffee",
                "Caramel Macchiato",
                25.5,
                75,
                "Sweet caramel flavored coffee"
        );

        List<ProductEntity> initProducts = List.of(blueBottle, lavazza, starbucks);

        // Initialize Customers
        CustomerEntity exampleCustomer = new CustomerEntity(
                UUID.randomUUID().toString(),
                "John Doe",
                "john.doe@example.com",
                "+1 123-456-7890",
                "123 Main Street, Jakarta, Indonesia",
                LocalDateTime.now()
        );

        CustomerEntity janeDoe = new CustomerEntity(
                UUID.randomUUID().toString(),
                "Jane Doe",
                "jane.doe@example.com",
                "+1 098-765-4321",
                "456 Oak Avenue, Bandung, Indonesia",
                LocalDateTime.now()
        );

        List<CustomerEntity> initCustomers = List.of(exampleCustomer, janeDoe);

        try {
            // Save products and customers first
            List<ProductEntity> savedProducts = productRepo.saveAll(initProducts);
            List<CustomerEntity> savedCustomers = customerRepo.saveAll(initCustomers);

            log.info("Saved {} products and {} customers", savedProducts.size(), savedCustomers.size());

            // Initialize Orders - wait until products and customers are saved
            ProductEntity savedLavazza = savedProducts.get(1); // lavazza
            ProductEntity savedBlueBottle = savedProducts.get(0); // blue bottle
            CustomerEntity savedCustomer = savedCustomers.get(0); // john doe

            OrderEntity order1 = new OrderEntity();
            order1.setId(UUID.randomUUID().toString());
            order1.setOrderNumber("ORD-" + System.currentTimeMillis());
            order1.setOrderDate(LocalDateTime.now());
            order1.setQuantity(2);
            order1.setUnitPrice(savedLavazza.getPrice());
            order1.setTotalPrice(savedLavazza.getPrice() * 2);
            order1.setStatus("COMPLETED");
            order1.setNotes("First order - please pack carefully");
            order1.setCustomer(savedCustomer);
            order1.setProduct(savedLavazza);

            OrderEntity order2 = new OrderEntity();
            order2.setId(UUID.randomUUID().toString());
            order2.setOrderNumber("ORD-" + (System.currentTimeMillis() + 1));
            order2.setOrderDate(LocalDateTime.now().minusDays(1));
            order2.setQuantity(1);
            order2.setUnitPrice(savedBlueBottle.getPrice());
            order2.setTotalPrice(savedBlueBottle.getPrice());
            order2.setStatus("PROCESSING");
            order2.setNotes("Second order - urgent delivery");
            order2.setCustomer(savedCustomer);
            order2.setProduct(savedBlueBottle);

            List<OrderEntity> initOrders = List.of(order1, order2);

            // Save orders
            List<OrderEntity> savedOrders = orderRepo.saveAll(initOrders);
            log.info("Saved {} orders", savedOrders.size());

            // Update product stocks after orders
            savedLavazza.setStock(savedLavazza.getStock() - 2); // Reduce stock for order1
            savedBlueBottle.setStock(savedBlueBottle.getStock() - 1); // Reduce stock for order2
            productRepo.saveAll(List.of(savedLavazza, savedBlueBottle));

            log.info("Database initialization completed successfully!");
            log.info("Sample Data:");
            log.info("- Products: {} items", savedProducts.size());
            log.info("- Customers: {} accounts", savedCustomers.size());
            log.info("- Orders: {} transactions", savedOrders.size());

        } catch (Exception e) {
            log.error("Database initialization failed, error: {}", e.getMessage());
            e.printStackTrace(); // Add this to see full stack trace
        }
    }
}
