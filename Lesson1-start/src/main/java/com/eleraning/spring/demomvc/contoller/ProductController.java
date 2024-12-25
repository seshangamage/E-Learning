package com.eleraning.spring.demomvc.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eleraning.spring.demomvc.model.Product;
import com.eleraning.spring.demomvc.model.ProductDto;
import com.eleraning.spring.demomvc.services.ProductRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



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

    @GetMapping("/create")
    public String getCreateProdcutPage(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/createProdcut";
    }

    @PostMapping("/create")
    public String createProdcut(@Valid @ModelAttribute ProductDto productDto,BindingResult result) {
        if(productDto.getImageFile() == null || productDto.getImageFile().isEmpty()){
            result.addError(new FieldError("prodcutDto", "imageFile", "The Image file isn empty"));
        }
        if(result.hasErrors()){
            return "products/createProdcut";
        }
        return "redirect:/products";
    }
    
    
}
