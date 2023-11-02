package com.example.Calculator_Project.contrroller;

import com.example.Calculator_Project.model.AppUser;
import com.example.Calculator_Project.repository.CalculationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class BaseController {

    AppUser getLoggedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) authentication.getPrincipal();;
        return appUser;
    }
}
