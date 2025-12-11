package com.gentara.system_meatballs.customer.controller_api;

import com.gentara.system_meatballs.customer.model.CustomerModel;
import com.gentara.system_meatballs.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerApiController {
    private CustomerService customerService;

    public CustomerApiController(CustomerService customerService){this.customerService = customerService;}

    @GetMapping
    public List<CustomerModel>getAll(){
            List<CustomerModel> result = customerService.getAll();
            return result;
    }
    @GetMapping("/{id}")
    public CustomerModel getById(@PathVariable("id") String id){
        CustomerModel result = this.customerService.getById(id);
        return result;
    }
    @PostMapping
    public CustomerModel create(@RequestBody CustomerModel request){
        CustomerModel result = this.customerService.save(request);
        return result;
    }
    @PatchMapping("/{id}")
    public CustomerModel edit(@PathVariable("id") String id,@RequestBody CustomerModel request){
        CustomerModel result = this.customerService.update(id,request);
        return result;
    }


}
