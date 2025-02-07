package com.eleraning.spring.demomvc.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.eleraning.spring.demomvc.model.Product;
import com.eleraning.spring.demomvc.services.ProductRepository;

@Controller
public class MainContoller {

    @Autowired
    private ProductRepository prodcutRepo;


    @GetMapping("/")
    public String home(Model model) {
        List<Product> productList = prodcutRepo.findAll();
        model.addAttribute("products", productList);
        return "index";
    }
}
