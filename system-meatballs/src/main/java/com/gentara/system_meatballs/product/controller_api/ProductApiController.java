package com.gentara.system_meatballs.product.controller_api;

import com.gentara.system_meatballs.product.model.ProductModel;
import com.gentara.system_meatballs.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductApiController {
    private ProductService productService;

    public ProductApiController(ProductService productService) {this.productService = productService;}
    @GetMapping
    public List<ProductModel> getAll(){
        List<ProductModel>result = productService.getAll();
        return result;
    }
    @GetMapping("/{id}")
    public ProductModel getById(@PathVariable("id") String id){
        ProductModel result = this.productService.getById(id);
        return result;
    }
    @PostMapping
    public ProductModel create(@RequestBody ProductModel request){
        ProductModel result = this.productService.save(request);
        return result;
    }
    @PatchMapping("/{id}")
    public ProductModel edit(@PathVariable("id") String id, @RequestBody ProductModel request){
        ProductModel result = this.productService.update(id,request);
        return result;
    }
    @DeleteMapping("/{id}")
    public ProductModel delete(@PathVariable("id")String id){
        ProductModel result = this.productService.delete(id);
        return result;
    }

}
