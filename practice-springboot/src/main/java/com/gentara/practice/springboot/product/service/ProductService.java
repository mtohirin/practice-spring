package com.gentara.practice.springboot.product.service;

import com.gentara.practice.springboot.product.model.ProductModel;

import java.util.List;

public interface ProductService {
    // Basic CRUD operations
    List<ProductModel> getAll();
    ProductModel getById(String id);
    ProductModel create(ProductModel request);
    ProductModel update(String id, ProductModel request);
    void delete(String id);

    // Additional business operations
    List<ProductModel> getAvailableProducts();
    List<ProductModel> getActiveProducts();
    List<ProductModel> findByBrand(String brand);
    List<ProductModel> findByType(String type);
    List<ProductModel> findByBrandAndType(String brand, String type);
    List<ProductModel> findByPriceRange(double minPrice, double maxPrice);
    ProductModel updateStock(String id, int newStock);
    ProductModel activateProduct(String id);
    ProductModel deactivateProduct(String id);
}