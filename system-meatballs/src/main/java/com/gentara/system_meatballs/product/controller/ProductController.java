package com.gentara.system_meatballs.product.controller;

import com.gentara.system_meatballs.product.model.ProductModel;
import com.gentara.system_meatballs.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {this.productService = productService;}

    @GetMapping
    public ModelAndView getAll(){
        ModelAndView view = new ModelAndView("/pages/product/index");
        List<ProductModel> result = productService.getAll();

        view.addObject("products", result);
        return view;
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("pages/product/add");
    }
    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute ProductModel productModel){
        this.productService.save(productModel);
        return new ModelAndView("redirect:/product");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        ProductModel productModel = this.productService.getById(id);
        if (productModel == null){
            return new ModelAndView("redirect:/product");
        }
        ModelAndView view = new ModelAndView("/pages/product/edit");
        view.addObject("product", productModel);
        return view;
    }
    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute ProductModel productModel){
        this.productService.update((productModel.getId()), productModel);
        return new ModelAndView("redirect:/product");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id")String id){
        ProductModel productModel = this.productService.getById(id);
        if (productModel == null){
            return new ModelAndView("/pages/product/delete");
        }
        ModelAndView view = new ModelAndView("/pages/product/delete");
        view.addObject("product", productModel);
        return view;
    }
    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute ProductModel productModel){
        this.productService.delete(productModel.getId());
        return new ModelAndView("redirect:/product");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id")String id){
        ProductModel productModel = this.productService.getById(id);
        if (productModel == null){
            return new ModelAndView("redirect:/product");
        }
        ModelAndView view = new ModelAndView("/pages/product/detail");
        view.addObject("product", productModel);
        return view;
    }

}
