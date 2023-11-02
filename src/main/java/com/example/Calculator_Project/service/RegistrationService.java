package com.example.Calculator_Project.service;

import com.example.Calculator_Project.model.form.RegistrationRequest;

public interface RegistrationService {
    public boolean emailAddressExists(String email);

    public String register(RegistrationRequest request);



}
