package com.marlonnunes.carrental.service;

import com.marlonnunes.carrental.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


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

    private String buildBodyEmailToResetPassword(User user, String idCredential){
        String body = "<h2>Ol&aacute;, {username}.</h2>\n" +
                "\n" +
                "<p><span style=\"font-size:14px\"><span style=\"font-family:arial,helvetica,sans-serif\">Abaixo est&atilde;o os dados para voc&ecirc; resetar sua senha:</span></span></p>\n" +
                "\n" +
                "<p><span style=\"font-size:16px\"><strong>C&oacute;digo de verifica&ccedil;&atilde;o:</strong>&nbsp;{verification_code}<br />\n" +
                "<strong>ID da sua credencial:</strong>&nbsp;{credential_id}</span></p>\n" +
                "\n" +
                "<p>Voc&ecirc; tem at&eacute; o dia {valid_until}&nbsp;para utilizar estes dados. Ap&oacute;s a data, ser&aacute; preciso repetir o processo para resetar a senha.</p>\n" +
                "\n" +
                "<p><strong><span style=\"font-size:14px\">Caso voc&ecirc; n&atilde;o tenha solicitado altera&ccedil;&atilde;o de senha, desconsidere este e-mail e n&atilde;o passe os dados para ningu&eacute;m.</span></strong></p>\n";

        body = body.replace("{username}", user.getFirstName());
        body = body.replace("{verification_code}", user.getVerificationCode());
        body = body.replace("{credential_id}", idCredential);
        body = body.replace("{valid_until}", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(user.getVerificationCodeValidUntil()));

        return body;
    }
}
