package com.miguel.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterForm {

    @NotEmpty(message = "Informe um nome de usuário.")
    @Size(min = 2, max = 100, message = "O usuário deve ter entre 2 e 100 caracteres.")
    private String username;

    @NotEmpty(message = "Informe um e-mail.")
    @Email(message = "Informe um e-mail válido.")
    private String email;

    @NotEmpty(message = "Informe uma senha.")
    @Size(min = 6, max = 100, message = "A senha deve ter pelo menos 6 caracteres.")
    private String password;

    @NotEmpty(message = "Confirme a sua senha.")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
