package com.eleraning.spring.demomvc.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eleraning.spring.demomvc.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{
    
}
