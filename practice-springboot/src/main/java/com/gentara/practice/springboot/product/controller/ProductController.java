package com.gentara.practice.springboot.product.controller;

import com.gentara.practice.springboot.product.model.ProductModel;
import com.gentara.practice.springboot.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET ALL PRODUCTS
    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        try {
            List<ProductModel> products = productService.getAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET PRODUCT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable String id) {
        try {
            ProductModel product = productService.getById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE NEW PRODUCT
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductModel request) {
        try {
            ProductModel createdProduct = productService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create product"));
        }
    }

    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductModel request) {
        try {
            ProductModel updatedProduct = productService.update(id, request);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update product"));
        }
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok().body(Map.of("message", "Product deleted successfully"));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete product"));
        }
    }

    // GET AVAILABLE PRODUCTS (with stock)
    @GetMapping("/available")
    public ResponseEntity<List<ProductModel>> getAvailableProducts() {
        try {
            List<ProductModel> products = productService.getAvailableProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET ACTIVE PRODUCTS
    @GetMapping("/active")
    public ResponseEntity<List<ProductModel>> getActiveProducts() {
        try {
            List<ProductModel> products = productService.getActiveProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH PRODUCTS BY BRAND
    @GetMapping("/search/brand")
    public ResponseEntity<List<ProductModel>> searchByBrand(@RequestParam String brand) {
        try {
            List<ProductModel> products = productService.findByBrand(brand);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH PRODUCTS BY TYPE
    @GetMapping("/search/type")
    public ResponseEntity<List<ProductModel>> searchByType(@RequestParam String type) {
        try {
            List<ProductModel> products = productService.findByType(type);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH PRODUCTS BY BRAND AND TYPE
    @GetMapping("/search/brand-type")
    public ResponseEntity<List<ProductModel>> searchByBrandAndType(
            @RequestParam String brand,
            @RequestParam String type) {
        try {
            List<ProductModel> products = productService.findByBrandAndType(brand, type);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH PRODUCTS BY PRICE RANGE
    @GetMapping("/search/price-range")
    public ResponseEntity<?> searchByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        try {
            List<ProductModel> products = productService.findByPriceRange(minPrice, maxPrice);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE STOCK
    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable String id, @RequestBody Map<String, Integer> request) {
        try {
            Integer newStock = request.get("stock");
            if (newStock == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Stock value is required"));
            }
            ProductModel updatedProduct = productService.updateStock(id, newStock);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update stock"));
        }
    }

    // ACTIVATE PRODUCT
    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateProduct(@PathVariable String id) {
        try {
            ProductModel updatedProduct = productService.activateProduct(id);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to activate product"));
        }
    }

    // DEACTIVATE PRODUCT
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateProduct(@PathVariable String id) {
        try {
            ProductModel updatedProduct = productService.deactivateProduct(id);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to deactivate product"));
        }
    }
}
