package com.gentara.system_meatballs.order.service;

import com.gentara.system_meatballs.order.model.OrderModel;

import java.util.List;

public interface OrderService {
    List<OrderModel> getAll();
    OrderModel getById(String id);
    OrderModel save(OrderModel request);
    OrderModel update(String id, OrderModel request);
    OrderModel delete(String id);
}
