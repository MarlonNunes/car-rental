package com.marlonnunes.carrental.service;

import com.marlonnunes.carrental.dto.registration.ResetPasswordDTO;
import com.marlonnunes.carrental.keycloak.KeycloakAPI;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class RegistrationService {
    @Autowired
    private UserService userService;

    @Autowired
    private KeycloakAPI keycloakAPI;

    @Autowired EmailService emailService;



    public ResponseEntity<Void> sendEmailToResetPassword(String email) {
        User user = this.userService.getUserByEmail(email);
        String userCredentialId = this.keycloakAPI.getUserCredentialsDetails(user.getKeycloakId());

        String verificationCode = StringUtils.verificationCodeGenerator();

        user.setVerificationCode(verificationCode);
        user.setVerificationCodeValidUntil(LocalDateTime.now().plusDays(2));

        user = userService.saveUser(user);

        this.emailService.sendEmailToResetPassword(user, userCredentialId);

        return ResponseEntity.ok(null);
    }

    public ResponseEntity<Void> resetPassword(ResetPasswordDTO resetPassword) {

        User user = this.userService.getById(resetPassword.userId());

        if(!resetPassword.verificationCode().equals(user.getVerificationCode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código de verificação inválido");
        }

        if(user.getVerificationCodeValidUntil().isBefore(LocalDateTime.now())){
            this.sendEmailToResetPassword(user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código expirado. Você receberá um novo e-mail atualizado");
        }

        return this.keycloakAPI.resetPassword(user.getKeycloakId(), resetPassword.credentialId(), resetPassword.password());
    }


}
