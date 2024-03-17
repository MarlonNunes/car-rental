package com.marlonnunes.carrental.service;

import com.marlonnunes.carrental.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.utils.IoUtils;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResourceLoader resourceLoader;


    private void sendEmail(String to, String body, String subject){
        MimeMessage mimeMesage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMesage, "utf-8");

        try{
            helper.setTo(to);
            helper.setText(body, true);
            helper.setSubject(subject);
        }catch (MessagingException e){
            log.error("An error occurred when building the email body. Body: {}", body, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao enviar e-mail");
        }

        this.mailSender.send(mimeMesage);

    }

    public void sendEmailToResetPassword(User user, String idCredentials){
        String body = this.buildBodyEmailToResetPassword(user, idCredentials);

        this.sendEmail(user.getEmail(), body, "CAR-RENTAL: RESETE SUA SENHA");
    }

    public void sendEmailToCreatePassword(User user) {
        String body = this.buildBodyEmailToCreatePassword(user);
        this.sendEmail(user.getEmail(), body, "CAR-RENTAL: CRIE SUA CONTA");
    }

    private String buildBodyEmailToResetPassword(User user, String idCredential){
        Resource resource = resourceLoader.getResource("classpath:/templates/reset-password.html");
        String body = null;
        try {
            body = IoUtils.toUtf8String(resource.getInputStream());
        } catch (IOException e) {
            log.error("An error occurred while converting reset-password.html file", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao enviar e-mail para resetar a senha");
        }

        return this.changeEmailVariables(body, user, idCredential);
    }

    private String buildBodyEmailToCreatePassword(User user){
        Resource resource = resourceLoader.getResource("classpath:/templates/create-password.html");
        String body = null;
        try {
            body = IoUtils.toUtf8String(resource.getInputStream());
        } catch (IOException e) {
            log.error("An error occurred while converting create-password.html file", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao enviar e-mail de criação de senha");
        }

        return this.changeEmailVariables(body, user, "");

    }

    private String changeEmailVariables(String body, User user, String idCredential){
        body = body.replace("{username}", user.getFirstName());
        body = body.replace("{verification_code}", user.getVerificationCode());
        body = body.replace("{credential_id}", idCredential);
        body = body.replace("{valid_until}", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(user.getVerificationCodeValidUntil()));

        return body;
    }
}
