package com.miguel.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RecoverPasswordForm {

    @NotEmpty(message = "Informe o e-mail cadastrado.")
    @Email(message = "Informe um e-mail válido.")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
