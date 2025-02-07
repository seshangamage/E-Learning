package com.eleraning.spring.demomvc.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eleraning.spring.demomvc.model.AppUser;

public interface UserRepository  extends JpaRepository<AppUser,Integer>{

    public AppUser findByEmail(String email);

    
}
