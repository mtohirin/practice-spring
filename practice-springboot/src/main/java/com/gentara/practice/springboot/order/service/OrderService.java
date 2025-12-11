package com.gentara.practice.springboot.order.service;

import com.gentara.practice.springboot.order.model.OrderRequest;
import com.gentara.practice.springboot.order.model.OrderModel;
import com.gentara.practice.springboot.order.model.OrderRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    // Basic CRUD operations
    List<OrderModel> getAll();
    OrderModel getById(String id);
    OrderModel getByOrderNumber(String orderNumber);
    OrderModel create(OrderRequest request);
    OrderModel updateStatus(String id, String status);
    void delete(String id);

    // Additional business operations
    List<OrderModel> getByCustomerId(String customerId);
    List<OrderModel> getByProductId(String productId);
    List<OrderModel> getByStatus(String status);
    List<OrderModel> getByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<OrderModel> getByCustomerAndStatus(String customerId, String status);

    // Business logic operations
    OrderModel confirmOrder(String id);
    OrderModel processOrder(String id);
    OrderModel completeOrder(String id);
    OrderModel cancelOrder(String id);

    // Reporting operations
    Double getTotalRevenue();
    Long getOrderCountByStatus(String status);
}
