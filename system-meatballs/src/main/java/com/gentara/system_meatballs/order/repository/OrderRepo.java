package com.gentara.system_meatballs.order.repository;

import com.gentara.system_meatballs.order.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<OrderEntity, String> {
}
