package com.example.Calculator_Project.service;

import com.example.Calculator_Project.constant.Pages;
import com.example.Calculator_Project.model.AppUser;
import com.example.Calculator_Project.model.constant.UserStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        AppUser appUser = (AppUser) authentication.getPrincipal();

        String redirectURL = request.getContextPath();

        if (appUser.isUser()) {
            redirectURL = Pages.User.ROUTE;
        } else if (appUser.isAdmin()) {
            redirectURL = Pages.Admin.ROUTE;
        }else {
            redirectURL = "404";

        }

        response.sendRedirect(redirectURL);


    }

}