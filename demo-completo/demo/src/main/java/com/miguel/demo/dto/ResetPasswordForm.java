package com.miguel.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ResetPasswordForm {

    @NotEmpty
    private String token;

    @NotEmpty(message = "Informe a nova senha.")
    @Size(min = 6, max = 100, message = "A senha deve ter pelo menos 6 caracteres.")
    private String password;

    @NotEmpty(message = "Confirme a nova senha.")
    private String confirmPassword;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
