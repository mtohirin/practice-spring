package com.gentara.system_meatballs.config;

import com.gentara.system_meatballs.customer.model.CustomerEntity;
import com.gentara.system_meatballs.customer.repository.CustomerRepo;
import com.gentara.system_meatballs.product.model.ProductEntity;
import com.gentara.system_meatballs.product.repository.ProductRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private ProductRepo productRepo;
    private CustomerRepo customerRepo;

    @Override
    public void run(String... args) throws Exception {
        initTables();
    }
    private void initTables(){
        //product
        ProductEntity baksoUrat = new ProductEntity("864cc836795a473e959d324342f7d969", "Bakso Urat", 15.000);
        ProductEntity baksoCincang = new ProductEntity("db26d77ec1754b04a61be4e907b44397", "Bakso Cincang", 15.000);
        ProductEntity baksoTelor = new ProductEntity("a3196c86fd804ba8a7910cdfe16734db", "Bakso Telur", 15.000);
        List<ProductEntity> initProduct = List.of(baksoUrat,baksoCincang, baksoTelor);

        //customer
        CustomerEntity johanLiebert = new CustomerEntity("156638bdac2648b4a3e2e4406ea14866", "Johan Liebert", "johan.liebert@example.com", "+49-876-987-894");
        List<CustomerEntity> initCustomer = List.of(johanLiebert);

        try {
          this.productRepo.saveAll(initProduct);
          this.customerRepo.saveAll(initCustomer);
          log.info("db initializer success");
        }catch (Exception e){
            log.error("db initializer failed, error {}",e.getMessage());
        }
    }
}
