package com.gentara.system_meatballs.order.service;

import com.gentara.system_meatballs.order.model.OrderEntity;
import com.gentara.system_meatballs.order.model.OrderModel;
import com.gentara.system_meatballs.order.repository.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private OrderRepo orderRepo;

    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public List<OrderModel> getAll() {
        List<OrderEntity> result = this.orderRepo.findAll();
        List<OrderModel> listModel = new ArrayList<>();
        for (OrderEntity entity : result) {
            OrderModel model = new OrderModel();
            model.setId(entity.getId());
            model.setProductId(entity.getProductId());
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
        result.setProductId(entity.getProductId());
        result.setCustomerId(entity.getCustomerId());
        result.setQuantity(entity.getQuantity());

        return result;
    }

    @Override
    public OrderModel save(OrderModel request) {
        OrderEntity entity = new OrderEntity();
        entity.setId(request.getId());
        entity.setProductId(request.getProductId());
        entity.setCustomerId(request.getCustomerId());
        entity.setQuantity(request.getQuantity());

        try {
            this.orderRepo.save(entity);
            log.info("save order success");
            OrderModel response = new OrderModel(entity.getId(), entity.getProductId(), entity.getCustomerId(), entity.getQuantity());
            return response;
        } catch (Exception e) {
            log.error("save order failed, error{}", e.getMessage());
            return new OrderModel();
        }
    }

    @Override
    public OrderModel update(String id, OrderModel request) {
        OrderEntity entity = this.orderRepo.findById(id).orElse(null);
        entity.setProductId(request.getProductId());
        entity.setCustomerId(request.getCustomerId());
        entity.setQuantity(request.getQuantity());
        try {
            this.orderRepo.save(entity);
            log.info("update order success");
            OrderModel respoponse = new OrderModel(entity.getId(), entity.getProductId(), entity.getCustomerId(), entity.getQuantity());
            return respoponse;
        } catch (Exception e) {
            log.error("update order failed, error {}", e.getMessage());
            return new OrderModel();
        }

    }
    @Override
    public OrderModel delete(String id){
        OrderEntity entity = this.orderRepo.findById(id).orElse(null);

        try {
            this.orderRepo.delete(entity);
            log.info("delete order success");
            OrderModel response = new OrderModel(entity.getId(), entity.getProductId(), entity.getCustomerId(), entity.getQuantity());
            return response;
        }catch (Exception e){
            log.error("delete order failed, error {}", e.getMessage());
            return new OrderModel();
        }
    }
}

