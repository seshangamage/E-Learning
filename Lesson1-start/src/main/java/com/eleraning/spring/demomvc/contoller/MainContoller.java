package com.eleraning.spring.demomvc.contoller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainContoller {


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("now", LocalDateTime.now().toString());
        return "index";
    }
    
}
