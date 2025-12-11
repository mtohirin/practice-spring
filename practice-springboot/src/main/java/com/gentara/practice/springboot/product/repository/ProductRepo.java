package com.gentara.practice.springboot.product.repository;

import com.gentara.practice.springboot.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, String> {

    // Cari product berdasarkan brand, type, dan flavor (untuk duplicate check)
    Optional<ProductEntity> findByBrandAndTypeAndFlavor(String brand, String type, String flavor);

    // Cari product by brand (case insensitive)
    List<ProductEntity> findByBrandContainingIgnoreCase(String brand);

    // Cari product by type (case insensitive)
    List<ProductEntity> findByTypeContainingIgnoreCase(String type);

    // Cari product yang masih active
    List<ProductEntity> findByIsActiveTrue();

    // Cari product dengan stock tersedia
    @Query("SELECT p FROM ProductEntity p WHERE p.stock > 0 AND p.isActive = true")
    List<ProductEntity> findAvailableProducts();

    // Cari product by price range
    @Query("SELECT p FROM ProductEntity p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.isActive = true")
    List<ProductEntity> findByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    // Cari product by brand dan type
    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.brand) LIKE LOWER(CONCAT('%', :brand, '%')) AND LOWER(p.type) LIKE LOWER(CONCAT('%', :type, '%')) AND p.isActive = true")
    List<ProductEntity> findByBrandAndType(@Param("brand") String brand, @Param("type") String type);

    // Update stock
    @Query("UPDATE ProductEntity p SET p.stock = :stock WHERE p.id = :id")
    void updateStock(@Param("id") String id, @Param("stock") int stock);
}