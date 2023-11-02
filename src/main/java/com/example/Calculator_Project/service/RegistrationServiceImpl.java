package com.example.Calculator_Project.service;

import com.example.Calculator_Project.constant.Pages;
import com.example.Calculator_Project.constant.RequestRouting;
import com.example.Calculator_Project.model.AppUser;
import com.example.Calculator_Project.model.constant.AppUserRole;
import com.example.Calculator_Project.model.form.RegistrationRequest;
import com.example.Calculator_Project.repository.AppUserRepository;
import com.example.Calculator_Project.util.GreetingUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AppUserService appUserService;


    @Override
    public boolean emailAddressExists(String email) {
        boolean userExists=appUserRepository.findByEmail(email).isEmpty();

        return  userExists;
    }

    @Override
    public String register(RegistrationRequest request) {

        if(request.getAppUserRole()  != AppUserRole.ADMIN){
            request.setAppUserRole(AppUserRole.USER);
        }

        appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getAppUserRole()


                ));

        if(request.getAppUserRole().equals(AppUserRole.ADMIN)){

            return Pages.Admin.SUCCESSFUL_ADMIN_ADDITION;
        }



        return RequestRouting.Registration.DONE_REGISTRATION;
    }



    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }


    public Optional<AppUser> getAppUser(Long id) {
        return appUserRepository.findById(id);
    }


}
