package com.eleraning.spring.demomvc.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.eleraning.spring.demomvc.model.AppUser;
import com.eleraning.spring.demomvc.model.RegisterDto;
import com.eleraning.spring.demomvc.services.AppUserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/register")
    public String getMethodName(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute("registerDto", registerDto);
        return "register";
    }

    @PostMapping("/register")
    public String addNewUser(Model model , @Valid @ModelAttribute RegisterDto registerDto , BindingResult result) {

        System.out.println(registerDto.getFirstName());
        if(!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
            result.addError(new FieldError("registerDto", "confirmPassword", "Password Miss Match"));
        }
        UserDetails userDetails = appUserService.getUserByEmail(registerDto.getEmail());
        if(userDetails != null){
            result.addError(new FieldError("registerDto", "email", "Email Address already Registerd"));
        }
        if(result.hasErrors()){
            return "register";
        }
        AppUser newAppUser = new AppUser();
        var bCryptEncoder = new BCryptPasswordEncoder();
        newAppUser.setEmail(registerDto.getEmail());
        newAppUser.setFirstName(registerDto.getFirstName());
        newAppUser.setLastName(registerDto.getLastName());
        newAppUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
        newAppUser.setRole("user");
        newAppUser.setCreatedAt(null);
        appUserService.addUseroDB(newAppUser);
        return "register";
    }
    
    
    
}
