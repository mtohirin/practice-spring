package com.gentara.system_meatballs.customer.service;

import com.gentara.system_meatballs.customer.model.CustomerEntity;
import com.gentara.system_meatballs.customer.model.CustomerModel;
import com.gentara.system_meatballs.customer.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepo customerRepo;

    public CustomerServiceImpl (CustomerRepo customerRepo){this.customerRepo = customerRepo;}
    @Override
    public List<CustomerModel> getAll() {
        List<CustomerEntity> result = this.customerRepo.findAll();
        List<CustomerModel> listModel = new ArrayList<>();
        for(CustomerEntity entity : result){
            CustomerModel model = new CustomerModel();
            model.setId(entity.getId());
            model.setName(entity.getName());
            model.setEmail(entity.getEmail());
            model.setPhone(entity.getPhone());
            listModel.add(model);
        }
        return listModel;
    }

    @Override
    public CustomerModel getById(String id) {
       CustomerEntity entity = this.customerRepo.findById(id).orElse(null);
       CustomerModel result = new CustomerModel();
       result.setId(entity.getId());
       result.setName(entity.getName());
       result.setEmail(entity.getEmail());
       result.setPhone(entity.getPhone());
       return result;
    }

    @Override
    public CustomerModel save(CustomerModel request) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());

        try {
            this.customerRepo.save(entity);
            log.info("save customer success");
            CustomerModel response = new CustomerModel(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone());
            return response;
        } catch (Exception e){
            log.error("save customer failed, error {}", e.getMessage());
            return new CustomerModel();
        }
    }

    @Override
    public CustomerModel update(String id, CustomerModel request) {
        CustomerEntity entity = this.customerRepo.findById(id).orElse(null);
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());

        try {
            this.customerRepo.save(entity);
            log.info("update costumer success");
            CustomerModel response = new CustomerModel(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone());
            return response;
        } catch (Exception e){
            log.error("update customer failed, error {}",e.getMessage());
            return new CustomerModel();
        }
    }

    @Override
    public CustomerModel delete(String id) {
        CustomerEntity entity = this.customerRepo.findById(id).orElse(null);

        try {
            this.customerRepo.delete(entity);
            log.info("delete customer success");
            CustomerModel response = new CustomerModel(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone());
            return response;
        } catch (Exception e){
            log.error("delete customer failed, error");
            return new CustomerModel();
        }
    }
}
