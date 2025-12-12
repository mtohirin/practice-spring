package com.gentara.system_meatballs.customer.controller;

import com.gentara.system_meatballs.customer.model.CustomerModel;
import com.gentara.system_meatballs.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/customer")
public class ControllerCustomer {
   private CustomerService customerService;

    public ControllerCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ModelAndView getAll(){
        ModelAndView view = new ModelAndView("/pages/customer/index");
        List<CustomerModel> result = customerService.getAll();

      view.addObject("customers", result);
        return view;
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("pages/customer/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute CustomerModel customerModel){
        this.customerService.save(customerModel);
        return new ModelAndView("redirect:/customer");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        CustomerModel customerModel = this.customerService.getById(id);
        if (customerModel == null){
            return new ModelAndView("redirect:/customer");
        }
        ModelAndView view = new ModelAndView("/pages/customer/edit");
        view.addObject("customer", customerModel);
        return view;
    }
    @PostMapping("/update")
    public ModelAndView edit(@ModelAttribute CustomerModel customerModel){
        this.customerService.update(customerModel.getId(),customerModel);
        return new ModelAndView("redirect:/customer");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id")String id){
        CustomerModel customerModel = this.customerService.getById(id);
        if (customerModel == null){
            return new ModelAndView("/pages/customer/delete");
        }
        ModelAndView view = new ModelAndView("/pages/customer/delete");
        view.addObject("customer", customerModel);
        return view;
    }
    @PostMapping("/remove")
    public ModelAndView delete(@ModelAttribute CustomerModel customerModel){
        this.customerService.delete(customerModel.getId());
        return new ModelAndView("redirect:/customer");
    }
    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id")String id){
        CustomerModel customerModel = this.customerService.getById(id);
        if (customerModel == null){
            return new ModelAndView("/pages/customer/detail");
        }
        ModelAndView view = new ModelAndView("/pages/customer/detail");
        view.addObject("customer", customerModel);
        return view;
    }
}
