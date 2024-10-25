package com.example.demo.validation.users;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ValidateUser {
    @Autowired
    private UserRepo userRepo;

    public void validate(User user) {
        String email = user.getEmail();
        String name = user.getName();

        User existingUser = userRepo.findByEmail(email);
        if (existingUser != null) {
            throw new DataIntegrityViolationException("Email already in use");
        }

        existingUser  = userRepo.findByName(name);
        if (existingUser != null) {
            throw new DataIntegrityViolationException("UserName already in use");
        }
    }
}
