package com.gentara.practice.springboot.order.service;

import com.gentara.practice.springboot.customer.model.CustomerEntity;
import com.gentara.practice.springboot.customer.repository.CustomerRepo;
import com.gentara.practice.springboot.order.model.OrderRequest;
import com.gentara.practice.springboot.order.model.OrderEntity;
import com.gentara.practice.springboot.order.model.OrderModel;
import com.gentara.practice.springboot.order.repository.OrderRepo;
import com.gentara.practice.springboot.product.model.ProductEntity;
import com.gentara.practice.springboot.product.repository.ProductRepo;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final ProductRepo productRepo;

    public OrderServiceImpl(OrderRepo orderRepo, CustomerRepo customerRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> getAll() {
        try {
            log.info("Fetching all orders");
            List<OrderEntity> result = orderRepo.findAll();
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting all orders: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve orders: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OrderModel getById(String id) {
        log.info("Fetching order by id: {}", id);
        OrderEntity entity = orderRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Order not found with id: {}", id);
                    return new RuntimeException("Order not found with id: " + id);
                });
        return convertToModel(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderModel getByOrderNumber(String orderNumber) {
        log.info("Fetching order by order number: {}", orderNumber);
        OrderEntity entity = orderRepo.findByOrderNumber(orderNumber)
                .orElseThrow(() -> {
                    log.warn("Order not found with order number: {}", orderNumber);
                    return new RuntimeException("Order not found with order number: " + orderNumber);
                });
        return convertToModel(entity);
    }

    @Override
    public OrderModel create(OrderRequest request) {
        log.info("Creating new order for customer: {}, product: {}", request.getCustomerId(), request.getProductId());

        // Validasi request
        if (request == null) {
            throw new RuntimeException("Order request cannot be null");
        }
        if (request.getCustomerId() == null || request.getCustomerId().trim().isEmpty()) {
            throw new RuntimeException("Customer ID is required");
        }
        if (request.getProductId() == null || request.getProductId().trim().isEmpty()) {
            throw new RuntimeException("Product ID is required");
        }
        if (request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        // Check customer exists and active
        CustomerEntity customer = customerRepo.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getCustomerId()));

        if (!customer.isActive()) {
            throw new RuntimeException("Customer is not active");
        }

        // Check product exists, active, and has enough stock
        ProductEntity product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));

        if (!product.isActive()) {
            throw new RuntimeException("Product is not active");
        }

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock. Available: " + product.getStock() + ", Requested: " + request.getQuantity());
        }

        // Calculate prices
        double unitPrice = product.getPrice();
        double totalPrice = unitPrice * request.getQuantity();

        // Generate order number
        String orderNumber = "ORD-" + System.currentTimeMillis();

        // Create order entity
        OrderEntity entity = new OrderEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setOrderNumber(orderNumber);
        entity.setOrderDate(LocalDateTime.now());
        entity.setQuantity(request.getQuantity());
        entity.setUnitPrice(unitPrice);
        entity.setTotalPrice(totalPrice);
        entity.setStatus("PENDING");
        entity.setNotes(request.getNotes());
        entity.setCustomer(customer);
        entity.setProduct(product);

        try {
            // Save order
            OrderEntity savedEntity = orderRepo.save(entity);

            // Update product stock
            int newStock = product.getStock() - request.getQuantity();
            product.setStock(newStock);
            productRepo.save(product);

            log.info("Order created successfully with order number: {}", orderNumber);
            return convertToModel(savedEntity);
        } catch (Exception e) {
            log.error("Failed to create order: {}", e.getMessage());
            throw new RuntimeException("Failed to create order: " + e.getMessage());
        }
    }

    @Override
    public OrderModel updateStatus(String id, String status) {
        log.info("Updating order status for id: {} to {}", id, status);

        // Validate status
        if (!isValidStatus(status)) {
            throw new RuntimeException("Invalid order status: " + status);
        }

        OrderEntity entity = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // Check if status transition is valid
        if (!isValidStatusTransition(entity.getStatus(), status)) {
            throw new RuntimeException("Invalid status transition from " + entity.getStatus() + " to " + status);
        }

        entity.setStatus(status);

        try {
            OrderEntity updatedEntity = orderRepo.save(entity);
            log.info("Order status updated successfully for id: {} to {}", id, status);
            return convertToModel(updatedEntity);
        } catch (Exception e) {
            log.error("Failed to update order status for id {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update order status: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        log.info("Deleting order with id: {}", id);
        OrderEntity entity = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // If order is not cancelled, restore product stock
        if (!"CANCELLED".equals(entity.getStatus())) {
            ProductEntity product = entity.getProduct();
            product.setStock(product.getStock() + entity.getQuantity());
            productRepo.save(product);
        }

        try {
            orderRepo.delete(entity);
            log.info("Order deleted successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Failed to delete order with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete order: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> getByCustomerId(String customerId) {
        try {
            log.info("Fetching orders for customer id: {}", customerId);
            List<OrderEntity> result = orderRepo.findByCustomerId(customerId);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting orders for customer {}: {}", customerId, e.getMessage());
            throw new RuntimeException("Failed to retrieve customer orders: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> getByProductId(String productId) {
        try {
            log.info("Fetching orders for product id: {}", productId);
            List<OrderEntity> result = orderRepo.findByProductId(productId);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting orders for product {}: {}", productId, e.getMessage());
            throw new RuntimeException("Failed to retrieve product orders: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> getByStatus(String status) {
        try {
            log.info("Fetching orders with status: {}", status);
            if (!isValidStatus(status)) {
                throw new RuntimeException("Invalid order status: " + status);
            }
            List<OrderEntity> result = orderRepo.findByStatus(status);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting orders with status {}: {}", status, e.getMessage());
            throw new RuntimeException("Failed to retrieve orders by status: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> getByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            log.info("Fetching orders between {} and {}", startDate, endDate);
            if (startDate.isAfter(endDate)) {
                throw new RuntimeException("Start date cannot be after end date");
            }
            List<OrderEntity> result = orderRepo.findByOrderDateBetween(startDate, endDate);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting orders by date range: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve orders by date range: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> getByCustomerAndStatus(String customerId, String status) {
        try {
            log.info("Fetching orders for customer {} with status: {}", customerId, status);
            if (!isValidStatus(status)) {
                throw new RuntimeException("Invalid order status: " + status);
            }
            List<OrderEntity> result = orderRepo.findByCustomerIdAndStatus(customerId, status);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting orders for customer {} with status {}: {}", customerId, status, e.getMessage());
            throw new RuntimeException("Failed to retrieve customer orders by status: " + e.getMessage());
        }
    }

    @Override
    public OrderModel confirmOrder(String id) {
        return updateStatus(id, "CONFIRMED");
    }

    @Override
    public OrderModel processOrder(String id) {
        return updateStatus(id, "PROCESSING");
    }

    @Override
    public OrderModel completeOrder(String id) {
        return updateStatus(id, "COMPLETED");
    }

    @Override
    public OrderModel cancelOrder(String id) {
        log.info("Cancelling order with id: {}", id);
        OrderEntity entity = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // Restore product stock if order was not already cancelled
        if (!"CANCELLED".equals(entity.getStatus())) {
            ProductEntity product = entity.getProduct();
            product.setStock(product.getStock() + entity.getQuantity());
            productRepo.save(product);
        }

        entity.setStatus("CANCELLED");

        try {
            OrderEntity updatedEntity = orderRepo.save(entity);
            log.info("Order cancelled successfully with id: {}", id);
            return convertToModel(updatedEntity);
        } catch (Exception e) {
            log.error("Failed to cancel order with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to cancel order: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalRevenue() {
        try {
            Double revenue = orderRepo.getTotalRevenue();
            return revenue != null ? revenue : 0.0;
        } catch (Exception e) {
            log.error("Error calculating total revenue: {}", e.getMessage());
            throw new RuntimeException("Failed to calculate total revenue: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long getOrderCountByStatus(String status) {
        try {
            if (!isValidStatus(status)) {
                throw new RuntimeException("Invalid order status: " + status);
            }
            return orderRepo.countByStatus(status);
        } catch (Exception e) {
            log.error("Error counting orders with status {}: {}", status, e.getMessage());
            throw new RuntimeException("Failed to count orders by status: " + e.getMessage());
        }
    }

    private OrderModel convertToModel(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        OrderModel model = new OrderModel();
        model.setId(entity.getId());
        model.setOrderNumber(entity.getOrderNumber());
        model.setOrderDate(entity.getOrderDate());
        model.setQuantity(entity.getQuantity());
        model.setUnitPrice(entity.getUnitPrice());
        model.setTotalPrice(entity.getTotalPrice());
        model.setStatus(entity.getStatus());
        model.setNotes(entity.getNotes());

        // Customer info
        if (entity.getCustomer() != null) {
            model.setCustomerId(entity.getCustomer().getId());
            model.setCustomerName(entity.getCustomer().getName());
            model.setCustomerEmail(entity.getCustomer().getEmail());
        }

        // Product info
        if (entity.getProduct() != null) {
            model.setProductId(entity.getProduct().getId());
            model.setProductName(entity.getProduct().getBrand() + " " + entity.getProduct().getType());
            model.setProductBrand(entity.getProduct().getBrand());
            model.setProductType(entity.getProduct().getType());
        }

        return model;
    }

    private boolean isValidStatus(String status) {
        return List.of("PENDING", "CONFIRMED", "PROCESSING", "COMPLETED", "CANCELLED").contains(status);
    }

    private boolean isValidStatusTransition(String fromStatus, String toStatus) {
        // Define valid status transitions
        return switch (fromStatus) {
            case "PENDING" -> List.of("CONFIRMED", "CANCELLED").contains(toStatus);
            case "CONFIRMED" -> List.of("PROCESSING", "CANCELLED").contains(toStatus);
            case "PROCESSING" -> List.of("COMPLETED", "CANCELLED").contains(toStatus);
            case "COMPLETED" -> false; // Cannot change completed orders
            case "CANCELLED" -> false; // Cannot change cancelled orders
            default -> false;
        };
    }
}