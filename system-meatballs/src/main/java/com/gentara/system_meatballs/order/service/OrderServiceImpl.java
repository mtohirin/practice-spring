package com.gentara.system_meatballs.order.service;

import com.gentara.system_meatballs.order.model.OrderEntity;
import com.gentara.system_meatballs.order.model.OrderModel;
import com.gentara.system_meatballs.order.repository.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
    private OrderRepo orderRepo;

    public OrderServiceImpl (OrderRepo orderRepo){this.orderRepo = orderRepo;}

    @Override
    public List<OrderModel> getAll() {
        List<OrderEntity> result = this.orderRepo.findAll();
        List<OrderModel> listModel = new ArrayList<>();
        for (OrderEntity entity : result){
            OrderModel model = new OrderModel();
            model.setId(entity.getId());
            model.setProductid(entity.getProductId());
            model.setCustomerId(entity.getCustomerId());
            model.setQuantity(entity.getQuantity());
        }
        return listModel;
    }

    @Override
    public OrderModel getById(String id) {
       OrderEntity entity = this.orderRepo.findById(id).orElse(null);
       OrderModel result = new OrderModel();
       result.setId(entity.getId());
       result.setProductid(entity.getProductId());
       result.setCustomerId(entity.getCustomerId());
       result.setQuantity(entity.getQuantity());

       return result;
    }

    @Override
    public OrderModel save(OrderModel request) {
       OrderEntity entity = new OrderEntity();
       entity.setId(UUID.randomUUID().toString());
       entity.setCustomerId(request.getCustomerId());
       entity.setProductId(request.getCustomerId());
       entity.setQuantity(request.getQuantity());
       return request;
    }

    @Override
    public OrderModel update(String id, OrderModel request) {
        return null;
    }

    @Override
    public OrderModel delete(String id) {
        return null;
    }
}
