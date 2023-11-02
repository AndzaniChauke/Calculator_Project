package com.example.Calculator_Project.contrroller;

import com.example.Calculator_Project.constant.Pages;
import com.example.Calculator_Project.constant.RequestRouting;
import com.example.Calculator_Project.model.constant.AppUserRole;
import com.example.Calculator_Project.model.form.RegistrationRequest;
import com.example.Calculator_Project.model.form.SearchRequest;
import com.example.Calculator_Project.repository.CalculationsRepository;
import com.example.Calculator_Project.service.RegistrationService;
import com.example.Calculator_Project.util.GreetingUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController  extends BaseController{

    private final RegistrationService registrationService;
    protected  final CalculationsRepository calculationsRepository;

    @GetMapping(RequestRouting.Admin.ROUTE)
    public String adminHomePage(RegistrationRequest request, Model model) {



        model.addAttribute("greeting", GreetingUtil.getGreeting());
        model.addAttribute("userProfile", getLoggedUser());

        model.addAttribute("calculationsList", calculationsRepository.findAll());
        return Pages.Admin.ROUTE;
    }

    @GetMapping(RequestRouting.Admin.SEARCH_CALCULATIONS)
    public ModelAndView search_calculator(SearchRequest request, Model model) {
        model.addAttribute("request",request);

        ModelAndView goo = new ModelAndView(Pages.Admin.SEARCH_CALCULATIONS);
        model.addAttribute("greeting", GreetingUtil.getGreeting());
        model.addAttribute("userProfile", getLoggedUser());

        return goo;
    }

    @GetMapping(RequestRouting.Admin.ADD_ADMIN)
    public ModelAndView addAdmin(RegistrationRequest request, Model model) {

        model.addAttribute("request",request);
        ModelAndView goo = new ModelAndView(Pages.Admin.ADD_ADMIN);
        model.addAttribute("greeting", GreetingUtil.getGreeting());
        model.addAttribute("userProfile", getLoggedUser());

        return goo;
    }

    @PostMapping(RequestRouting.Admin.ADD_ADMIN)
    public String registerNewAdmin(@Valid @ModelAttribute("request") RegistrationRequest request, BindingResult result, Model model ){
        model.addAttribute("request", request);

        model.addAttribute("request",request);
        model.addAttribute("greeting", GreetingUtil.getGreeting());
        model.addAttribute("userProfile", getLoggedUser());
        if (result.hasErrors()) {
            return Pages.Admin.ADD_ADMIN;
        }


        if(registrationService.emailAddressExists(request.getEmail())==false) {
            result.addError(new ObjectError("emailAddress", "This Email Address "
                    + request.getEmail() + " already exist"));
            return Pages.Admin.ADD_ADMIN;
        }

        request.setAppUserRole(AppUserRole.ADMIN);

        return registrationService.register(request);
    }

    @PostMapping(RequestRouting.Admin.SEARCH_CALCULATIONS)
    public String search_calculator_post(SearchRequest request,BindingResult result, Model model) {
        model.addAttribute("request",request);

        model.addAttribute("greeting", GreetingUtil.getGreeting());
        model.addAttribute("userProfile", getLoggedUser());

        model.addAttribute("calculationsList",
                calculationsRepository.searchWithLikeAndTimeRange(request.getEmail(),request.getFromDate(),request.getToDate()));

        return Pages.Admin.ROUTE;
    }
}
