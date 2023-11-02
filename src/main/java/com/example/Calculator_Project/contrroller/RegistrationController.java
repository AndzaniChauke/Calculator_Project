package com.example.Calculator_Project.contrroller;

import com.example.Calculator_Project.constant.RequestRouting;
import com.example.Calculator_Project.model.form.RegistrationRequest;
import com.example.Calculator_Project.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@AllArgsConstructor
public class RegistrationController extends BaseController implements WebMvcConfigurer {
    private final RegistrationService registrationService;


    @PostMapping(RequestRouting.Registration.ROUTE)
    public String regis(@Valid @ModelAttribute("request") RegistrationRequest request, BindingResult result, Model model ){
        model.addAttribute("request", request);


        if (result.hasErrors()) {
            return RequestRouting.Registration.ROUTE;
        }

        if(registrationService.emailAddressExists(request.getEmail())==false) {
            result.addError(new ObjectError("emailAddress", "This Email Address "
                    + request.getEmail() + " already exist"));
            return RequestRouting.Registration.ROUTE;
        }

        return registrationService.register(request);
    }
}
