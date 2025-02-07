package com.eleraning.spring.demomvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.eleraning.spring.demomvc.model.AppUser;

@Service
public class AppUserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails getUserByEmail(String email){
        AppUser appUser = userRepository.findByEmail(email);
        

        if(appUser != null){
            var springUser = User.withUsername(appUser.getEmail())
                                .password(appUser.getPassword())
                                .roles(appUser.getRole())
                                .build();
            return springUser;
        }
        
        return null;
    }

    public void addUseroDB(AppUser newAppUser){
        userRepository.save(newAppUser);
    }


    
}
