package com.gentara.system_meatballs.product.service;

import com.gentara.system_meatballs.product.model.ProductModel;

import java.util.List;

public interface ProductService {
    List<ProductModel> getAll();
    ProductModel getById(String id);
    ProductModel save(ProductModel request);
    ProductModel update(String id, ProductModel request);
    ProductModel delete(String id);
}
