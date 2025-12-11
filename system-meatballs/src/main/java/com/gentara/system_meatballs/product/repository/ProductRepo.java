package com.gentara.system_meatballs.product.repository;

import com.gentara.system_meatballs.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, String>{
}
