package com.eleraning.spring.demomvc.contoller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.eleraning.spring.demomvc.model.Product;
import com.eleraning.spring.demomvc.model.ProductDto;
import com.eleraning.spring.demomvc.services.ProductRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;





@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository prodcutRepo;

    @Value("${uploadlocation}")
    private String uploadLocation;

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
        return "products/createProduct";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
        if (productDto.getImageFile() == null || productDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFile", "The Image file is empty"));
        }
        if (result.hasErrors()) {
            return "products/createProduct";
        }
    
        MultipartFile image = productDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
        try {
            Path uploadPath = Paths.get(uploadLocation);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
    
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadPath.toString(), storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception
        }
    
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(createdAt);
        product.setImageFileName(storageFileName);
        prodcutRepo.save(product);
    
        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String getEditProductPage(Model model , @RequestParam int id) {
        try{
            Product product = prodcutRepo.findById(id).get();
            model.addAttribute("product", product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());
            model.addAttribute("productDto", productDto);
        }
        catch(Exception e){

        }
        return "products/editProduct";
    }
    

    
    @PostMapping("/edit")
    public String updateProduct(@Valid @ModelAttribute ProductDto productDto , @RequestParam int id , BindingResult result , Model model) {

        try{
            Product product = prodcutRepo.findById(id).get();
            model.addAttribute("product",product);
            if(result.hasErrors()){
                return "products/editProduct";
            }
            if(!(productDto.getImageFile() == null || productDto.getImageFile().isEmpty())){
                try{
                    Files.delete(Paths.get(uploadLocation + product.getImageFileName()));
                    try {
                        Date createdAt = new Date();
                        String storageFileName = createdAt.getTime() + "_" + productDto.getImageFile().getOriginalFilename();
                        InputStream inputStream = productDto.getImageFile().getInputStream();
                        Files.copy(inputStream, Paths.get(uploadLocation, storageFileName), StandardCopyOption.REPLACE_EXISTING);
                        product.setImageFileName(storageFileName);
                        product.setName(productDto.getName());
                        product.setBrand(productDto.getBrand());
                        product.setCategory(productDto.getCategory());
                        product.setPrice(productDto.getPrice());
                        product.setDescription(productDto.getDescription());
                        prodcutRepo.save(product);
                    }
                    catch(Exception e){

                    }
                }
                catch(Exception e){

                }
            }

        }
        catch(Exception e){

        }
        return "redirect:/products";
    }
    
    
    
}
