package com.gentara.system_meatballs.product.service;

import com.gentara.system_meatballs.product.model.ProductEntity;
import com.gentara.system_meatballs.product.model.ProductModel;
import com.gentara.system_meatballs.product.repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    private ProductRepo productRepo;

   public ProductServiceImpl (ProductRepo productRepo){this.productRepo = productRepo;}

    @Override
    public List<ProductModel> getAll() {
        List<ProductEntity> result = this.productRepo.findAll();
        List<ProductModel> listModel = new ArrayList<>();
        for (ProductEntity entity : result){
            ProductModel model = new ProductModel();
            model.setId(entity.getId());
            model.setName(entity.getName());
            model.setPrice(entity.getPrice());
            listModel.add(model);
        }
        return listModel;
    }

    @Override
    public ProductModel getById(String id) {
       ProductEntity entity = this.productRepo.findById(id).orElse(null);
       ProductModel result = new ProductModel();
       result.setId(entity.getId());
       result.setName(entity.getName());
       result.setPrice(entity.getPrice());
       return result;
    }

    @Override
    public ProductModel save(ProductModel request) {
      ProductEntity entity = new ProductEntity();
      entity.setId(UUID.randomUUID().toString());
      entity.setName(request.getName());
      entity.setPrice(request.getPrice());

      try {
          this.productRepo.save(entity);
          log.info("product save success");
          ProductModel response = new ProductModel(entity.getId(), entity.getName(), entity.getPrice());
          return response;
      }catch (Exception e){
          log.error("save product failed, error {}",e.getMessage());
          return new ProductModel();
      }
    }

    @Override
    public ProductModel update(String id, ProductModel request) {
        ProductEntity entity = this.productRepo.findById(id).orElse(null);
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());

        try {
            this.productRepo.save(entity);
            log.info("product update success");
            ProductModel response = new ProductModel(entity.getId(), entity.getName(), entity.getPrice());
            return response;
        } catch (Exception e) {
            log.error("update product failed, error {}",e.getMessage());
            return new ProductModel();
        }
    }

    @Override
    public ProductModel delete(String id) {
       ProductEntity entity = this.productRepo.findById(id).orElse(null);

        try {
            this.productRepo.delete(entity);
            log.info("delete product success");
            ProductModel response = new ProductModel(entity.getId(), entity.getName(), entity.getPrice());
            return response;
        }catch (Exception e){
            log.error("delete product failed, error {}",e.getMessage());
            return new ProductModel();
        }
    }
}
