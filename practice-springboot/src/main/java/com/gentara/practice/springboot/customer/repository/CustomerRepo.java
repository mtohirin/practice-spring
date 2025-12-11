package com.gentara.practice.springboot.customer.repository;

import com.gentara.practice.springboot.customer.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {

    // Cari customer by email (exact match)
    Optional<CustomerEntity> findByEmail(String email);

    // Cari customer by name (case insensitive, partial match)
    List<CustomerEntity> findByNameContainingIgnoreCase(String name);

    // Cari customer by email (partial match)
    @Query("SELECT c FROM CustomerEntity c WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<CustomerEntity> findByEmailContaining(@Param("email") String email);

    // Cari customer yang masih active
    List<CustomerEntity> findByIsActiveTrue();

    // Cek apakah email sudah ada
    boolean existsByEmail(String email);

    // Cari customer by phone number
    List<CustomerEntity> findByPhoneContaining(String phone);

    // Hitung total customer active
    long countByIsActiveTrue();

    // Cari customer by name dan email
    @Query("SELECT c FROM CustomerEntity c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')) AND c.isActive = true")
    List<CustomerEntity> findByNameAndEmail(@Param("name") String name, @Param("email") String email);
}