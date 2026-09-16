package com.miguel.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miguel.demo.dto.RecoverPasswordForm;
import com.miguel.demo.dto.RegisterForm;
import com.miguel.demo.dto.ResetPasswordForm;
import com.miguel.demo.exceptions.UserAlreadyExistsException;
import com.miguel.demo.services.UserService;

import jakarta.validation.Valid;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm form,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "As senhas informadas não coincidem.");
        }

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.register(form);
        } catch (UserAlreadyExistsException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "auth/register";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Conta criada com sucesso! Faça login para continuar.");
        return "redirect:/login";
    }

    @GetMapping("/recoverpassword")
    public String recoverPasswordPage(Model model) {
        if (!model.containsAttribute("recoverPasswordForm")) {
            model.addAttribute("recoverPasswordForm", new RecoverPasswordForm());
        }
        return "auth/recover-password";
    }

    @PostMapping("/recoverpassword")
    public String recoverPassword(@Valid @ModelAttribute("recoverPasswordForm") RecoverPasswordForm form,
                                   BindingResult bindingResult,
                                   Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/recover-password";
        }

        userService.requestPasswordReset(form.getEmail());

       
        model.addAttribute("successMessage",
                "Se esse e-mail estiver cadastrado, você receberá um link de recuperação em instantes.");
        return "auth/recover-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam("token") String token, Model model) {
        if (!userService.isResetTokenValid(token)) {
            model.addAttribute("errorMessage", "Esse link de recuperação é inválido ou já expirou.");
            model.addAttribute("invalidToken", true);
            return "auth/reset-password";
        }

        ResetPasswordForm form = new ResetPasswordForm();
        form.setToken(token);
        model.addAttribute("resetPasswordForm", form);
        model.addAttribute("invalidToken", false);
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute("resetPasswordForm") ResetPasswordForm form,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "As senhas informadas não coincidem.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("invalidToken", false);
            return "auth/reset-password";
        }

        try {
            userService.resetPassword(form.getToken(), form.getPassword());
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("invalidToken", true);
            return "auth/reset-password";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Senha redefinida com sucesso! Faça login com a nova senha.");
        return "redirect:/login";
    }
}
