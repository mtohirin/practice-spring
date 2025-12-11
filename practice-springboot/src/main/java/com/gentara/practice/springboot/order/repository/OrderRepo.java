package com.gentara.practice.springboot.order.repository;

import com.gentara.practice.springboot.order.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, String> {

    // Cari order by order number
    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    // Cari order by customer
    List<OrderEntity> findByCustomerId(String customerId);

    // Cari order by product
    List<OrderEntity> findByProductId(String productId);

    // Cari order by status
    List<OrderEntity> findByStatus(String status);

    // Cari order by date range
    @Query("SELECT o FROM OrderEntity o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<OrderEntity> findByOrderDateBetween(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);

    // Cari order by customer dan status
    @Query("SELECT o FROM OrderEntity o WHERE o.customer.id = :customerId AND o.status = :status")
    List<OrderEntity> findByCustomerIdAndStatus(@Param("customerId") String customerId,
                                                @Param("status") String status);

    // Hitung total revenue dari order completed
    @Query("SELECT SUM(o.totalPrice) FROM OrderEntity o WHERE o.status = 'COMPLETED'")
    Double getTotalRevenue();

    // Hitung jumlah order by status
    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.status = :status")
    Long countByStatus(@Param("status") String status);

    // Cari order dengan customer dan product info (untuk reporting)
    @Query("SELECT o FROM OrderEntity o JOIN FETCH o.customer c JOIN FETCH o.product p WHERE o.id = :id")
    Optional<OrderEntity> findByIdWithCustomerAndProduct(@Param("id") String id);
}