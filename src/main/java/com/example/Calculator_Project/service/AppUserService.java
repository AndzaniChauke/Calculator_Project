package com.example.Calculator_Project.service;

import com.example.Calculator_Project.model.AppUser;
import com.example.Calculator_Project.model.constant.UserStatus;
import com.example.Calculator_Project.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND="USER WITH EMAIL %S NOT FOUND";

    private final AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(username)
                .orElseThrow(()->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND,username)));
    }

    public Optional<AppUser> getAppUser(Long id) {
        return appUserRepository.findById(id);
    }

    public boolean emailAddressExists(String email) {
        boolean userExists=appUserRepository.findByEmail(email).isEmpty();

        return  userExists;
    }


    public void signUpUser(AppUser appUser){

        String encodedPassword=bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUser.setEnabled(true);
        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();


    }


    public void signUpAmin(AppUser appUser){
        boolean userExists=appUserRepository.
                findByEmail(appUser.getEmail())
                .isPresent();


        if(userExists){
            throw new IllegalStateException("email already exist ");
        }

        String encodedPassword=bCryptPasswordEncoder.encode(appUser.getPassword());


        appUser.setPassword(encodedPassword);


        appUser.setEnabled(true);
        appUserRepository.save(appUser);




    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
