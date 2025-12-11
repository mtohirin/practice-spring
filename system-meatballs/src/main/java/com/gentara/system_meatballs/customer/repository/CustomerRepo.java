package com.gentara.system_meatballs.customer.repository;

import com.gentara.system_meatballs.customer.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity,String> {
}
