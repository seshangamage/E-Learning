package com.eleraning.spring.demomvc.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eleraning.spring.demomvc.model.Product;
import com.eleraning.spring.demomvc.services.ProductRepository;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository prodcutRepo;

    @GetMapping({"","/"})
    public String showProductList(Model model){
        List<Product> productList = prodcutRepo.findAll();
        model.addAttribute("products", productList);
        return "products/products";

    }
    
}
