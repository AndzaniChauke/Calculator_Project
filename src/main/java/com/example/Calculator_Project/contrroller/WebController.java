package com.example.Calculator_Project.contrroller;

import com.example.Calculator_Project.constant.Pages;
import com.example.Calculator_Project.constant.RequestRouting;
import com.example.Calculator_Project.model.form.RegistrationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {


    /**
     * Fetches Home Page
     * @return html
     */
    @GetMapping(RequestRouting.HOME)
    public String getIndexPage(){

        return Pages.HOME ;

    }

    /**
     * Fetches Login Page
     * @return html
     */
    @GetMapping(RequestRouting.Login.ROUTE)
    public String getLoginPage(){

        return Pages.LOGIN;

    }


    /**
     *
     * @param request form to be filled in
     * @param model {@link Model} spring mvc model
     * @return html
     */
    @GetMapping(RequestRouting.Registration.ROUTE)
    public String getRegistrationPage(RegistrationRequest request, Model model){

        model.addAttribute("request",request);
        return RequestRouting.Registration.ROUTE;

    }

    /**
     *
     * @return html
     */
    @GetMapping(RequestRouting.Registration.DONE_REGISTRATION)
    public String getRegistrationSuccessPage(){

        return RequestRouting.Registration.DONE_REGISTRATION;

    }

}
