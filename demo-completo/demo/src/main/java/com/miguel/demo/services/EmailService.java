package com.miguel.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromAddress;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public void sendPasswordResetEmail(String toEmail, String username, String token) {
        String link = baseUrl + "/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        if (fromAddress != null && !fromAddress.isBlank()) {
            message.setFrom(fromAddress);
        }
        message.setSubject("Recuperação de senha - Jornada");
        message.setText(
                "Olá, " + username + "!\n\n" +
                "Recebemos uma solicitação para redefinir a sua senha.\n" +
                "Clique no link abaixo para escolher uma nova senha (válido por 30 minutos):\n\n" +
                link + "\n\n" +
                "Se você não solicitou essa alteração, ignore este e-mail."
        );

        try {
            mailSender.send(message);
            log.info("E-mail de recuperação de senha enviado para {}", toEmail);
        } catch (Exception ex) {
           
            log.warn("Não foi possível enviar o e-mail de recuperação para {}. " +
                    "Configure spring.mail.* (MAIL_USERNAME/MAIL_PASSWORD) para habilitar o envio real. " +
                    "Link gerado: {}", toEmail, link);
        }
    }
}
