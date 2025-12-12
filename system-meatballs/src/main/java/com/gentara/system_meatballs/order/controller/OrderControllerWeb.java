package com.gentara.system_meatballs.order.controller;

import com.gentara.system_meatballs.order.model.OrderModel;
import com.gentara.system_meatballs.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderControllerWeb {
    private OrderService orderService;

    public OrderControllerWeb (OrderService orderService){this.orderService = orderService;}
    @GetMapping
    public ModelAndView getAll(){
        ModelAndView view = new ModelAndView("/pages/order/index");
        List<OrderModel> result = this.orderService.getAll();

        view.addObject("orders", result);
        return view;
    }
    @GetMapping("/add")
    public ModelAndView add(@PathVariable ("id")String id){
       return new ModelAndView("/pages/order/add");
    }
    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute OrderModel orderModel){
        this.orderService.save(orderModel);
        return new ModelAndView("redirect:/order");
    }
    @PostMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable ("id")String id){
        OrderModel orderModel = this.orderService.getById(id);
        if (orderModel == null){
            return new ModelAndView("redirect:/order");
        }
        ModelAndView view = new ModelAndView("/pages/order/edit");
        view.addObject("edit", orderModel);
        return new ModelAndView("redirect:/order");
    }
    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute OrderModel orderModel){
        this.orderService.update(orderModel.getId(), orderModel);
        return new ModelAndView("redirect:/order");
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id")String id){
        OrderModel orderModel= this.orderService.delete(id);
        if (orderModel == null){
            return new ModelAndView("redirect:/order");
        }
        ModelAndView view = new ModelAndView("/pages/order/delete");
        view.addObject("delete", orderModel);
        return new ModelAndView("redirect:/order");
    }
    @PostMapping("/remove")
    public ModelAndView delete(@ModelAttribute OrderModel orderModel){
        this.orderService.delete(orderModel.getId());
        return new ModelAndView("redirect:/order");
    }
    @GetMapping("/detail")
    public ModelAndView detail(@PathVariable("id")String id){
        OrderModel orderModel = this.orderService.getById(id);
        if (orderModel == null){
            return new ModelAndView("redirect:/order");
        }
        ModelAndView view = new ModelAndView("/pages/order/detail");
        view.addObject("detail", orderModel);
        return new ModelAndView("redirect:/order");
    }

}

