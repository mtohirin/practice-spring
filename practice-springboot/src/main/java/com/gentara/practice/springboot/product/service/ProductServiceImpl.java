package com.gentara.practice.springboot.product.service;

import com.gentara.practice.springboot.product.model.ProductEntity;
import com.gentara.practice.springboot.product.model.ProductModel;
import com.gentara.practice.springboot.product.repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> getAll() {
        try {
            log.info("Fetching all products");
            List<ProductEntity> result = productRepo.findAll();
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting all products: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve products: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductModel getById(String id) {
        log.info("Fetching product by id: {}", id);
        ProductEntity entity = productRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with id: {}", id);
                    return new RuntimeException("Product not found with id: " + id);
                });
        return convertToModel(entity);
    }

    @Override
    public ProductModel create(ProductModel request) {
        log.info("Creating new product: {} {} {}", request.getBrand(), request.getType(), request.getFlavor());
        validateProductRequest(request);

        // Check for duplicate product
        productRepo.findByBrandAndTypeAndFlavor(request.getBrand(), request.getType(), request.getFlavor())
                .ifPresent(existing -> {
                    log.warn("Duplicate product found: {} {} {}", request.getBrand(), request.getType(), request.getFlavor());
                    throw new RuntimeException("Product with same brand, type, and flavor already exists");
                });

        ProductEntity entity = new ProductEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setBrand(request.getBrand().trim());
        entity.setType(request.getType().trim());
        entity.setFlavor(request.getFlavor() != null ? request.getFlavor().trim() : null);
        entity.setPrice(request.getPrice());
        entity.setStock(request.getStock());
        entity.setDescription(request.getDescription());
        entity.setActive(true);

        try {
            ProductEntity savedEntity = productRepo.save(entity);
            log.info("Product created successfully with id: {}", savedEntity.getId());
            return convertToModel(savedEntity);
        } catch (Exception e) {
            log.error("Failed to create product: {}", e.getMessage());
            throw new RuntimeException("Failed to create product: " + e.getMessage());
        }
    }

    @Override
    public ProductModel update(String id, ProductModel request) {
        log.info("Updating product with id: {}", id);
        validateProductRequest(request);

        ProductEntity entity = productRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found for update with id: {}", id);
                    return new RuntimeException("Product not found with id: " + id);
                });

        // Check for duplicate (excluding current product)
        productRepo.findByBrandAndTypeAndFlavor(request.getBrand(), request.getType(), request.getFlavor())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        log.warn("Duplicate product found during update: {} {} {}", request.getBrand(), request.getType(), request.getFlavor());
                        throw new RuntimeException("Another product with same brand, type, and flavor already exists");
                    }
                });

        entity.setBrand(request.getBrand().trim());
        entity.setType(request.getType().trim());
        entity.setFlavor(request.getFlavor() != null ? request.getFlavor().trim() : null);
        entity.setPrice(request.getPrice());
        entity.setStock(request.getStock());
        entity.setDescription(request.getDescription());

        try {
            ProductEntity updatedEntity = productRepo.save(entity);
            log.info("Product updated successfully with id: {}", id);
            return convertToModel(updatedEntity);
        } catch (Exception e) {
            log.error("Failed to update product with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update product: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        log.info("Deleting product with id: {}", id);
        ProductEntity entity = productRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found for deletion with id: {}", id);
                    return new RuntimeException("Product not found with id: " + id);
                });

        try {
            productRepo.delete(entity);
            log.info("Product deleted successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Failed to delete product with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete product: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> getAvailableProducts() {
        try {
            log.info("Fetching available products (with stock)");
            List<ProductEntity> result = productRepo.findAvailableProducts();
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting available products: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve available products: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> getActiveProducts() {
        try {
            log.info("Fetching active products");
            List<ProductEntity> result = productRepo.findByIsActiveTrue();
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting active products: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve active products: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> findByBrand(String brand) {
        try {
            log.info("Finding products by brand: {}", brand);
            List<ProductEntity> result = productRepo.findByBrandContainingIgnoreCase(brand);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding products by brand {}: {}", brand, e.getMessage());
            throw new RuntimeException("Failed to find products by brand: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> findByType(String type) {
        try {
            log.info("Finding products by type: {}", type);
            List<ProductEntity> result = productRepo.findByTypeContainingIgnoreCase(type);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding products by type {}: {}", type, e.getMessage());
            throw new RuntimeException("Failed to find products by type: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> findByBrandAndType(String brand, String type) {
        try {
            log.info("Finding products by brand: {} and type: {}", brand, type);
            List<ProductEntity> result = productRepo.findByBrandAndType(brand, type);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding products by brand {} and type {}: {}", brand, type, e.getMessage());
            throw new RuntimeException("Failed to find products by brand and type: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> findByPriceRange(double minPrice, double maxPrice) {
        try {
            log.info("Finding products by price range: {} - {}", minPrice, maxPrice);
            if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
                throw new IllegalArgumentException("Invalid price range");
            }
            List<ProductEntity> result = productRepo.findByPriceRange(minPrice, maxPrice);
            return result.stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding products by price range {}-{}: {}", minPrice, maxPrice, e.getMessage());
            throw new RuntimeException("Failed to find products by price range: " + e.getMessage());
        }
    }

    @Override
    public ProductModel updateStock(String id, int newStock) {
        log.info("Updating stock for product id {} to {}", id, newStock);
        ProductEntity entity = productRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found for stock update with id: {}", id);
                    return new RuntimeException("Product not found with id: " + id);
                });

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        entity.setStock(newStock);

        try {
            ProductEntity updatedEntity = productRepo.save(entity);
            log.info("Stock updated successfully for product id {}: new stock = {}", id, newStock);
            return convertToModel(updatedEntity);
        } catch (Exception e) {
            log.error("Failed to update stock for product id {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update stock: " + e.getMessage());
        }
    }

    @Override
    public ProductModel activateProduct(String id) {
        log.info("Activating product with id: {}", id);
        return setProductActiveStatus(id, true);
    }

    @Override
    public ProductModel deactivateProduct(String id) {
        log.info("Deactivating product with id: {}", id);
        return setProductActiveStatus(id, false);
    }

    private ProductModel setProductActiveStatus(String id, boolean isActive) {
        ProductEntity entity = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        entity.setActive(isActive);

        try {
            ProductEntity updatedEntity = productRepo.save(entity);
            log.info("Product {} successfully with id: {}", isActive ? "activated" : "deactivated", id);
            return convertToModel(updatedEntity);
        } catch (Exception e) {
            log.error("Failed to {} product with id {}: {}", isActive ? "activate" : "deactivate", id, e.getMessage());
            throw new RuntimeException("Failed to " + (isActive ? "activate" : "deactivate") + " product: " + e.getMessage());
        }
    }

    private ProductModel convertToModel(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ProductModel(
                entity.getId(),
                entity.getBrand(),
                entity.getType(),
                entity.getFlavor(),
                entity.getPrice(),
                entity.getStock(),
                entity.getDescription(),
                entity.isActive()
        );
    }

    private void validateProductRequest(ProductModel request) {
        if (request == null) {
            throw new IllegalArgumentException("Product request cannot be null");
        }
        if (request.getBrand() == null || request.getBrand().trim().isEmpty()) {
            throw new IllegalArgumentException("Brand is required");
        }
        if (request.getType() == null || request.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Type is required");
        }
        if (request.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (request.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }
}
