package com.marlonnunes.carrental.service;

import com.marlonnunes.carrental.dto.commons.IdNameDTO;
import com.marlonnunes.carrental.dto.keycloak.KeycloakRoleDTO;
import com.marlonnunes.carrental.dto.keycloak.SaveUserKeycloakDTO;
import com.marlonnunes.carrental.dto.keycloak.UserKeycloakDTO;
import com.marlonnunes.carrental.dto.registration.CreatePasswordDTO;
import com.marlonnunes.carrental.dto.registration.ResetPasswordDTO;
import com.marlonnunes.carrental.dto.user.CreateUserDTO;
import com.marlonnunes.carrental.dto.user.RoleDTO;
import com.marlonnunes.carrental.dto.user.UserDTO;
import com.marlonnunes.carrental.keycloak.KeycloakAPI;
import com.marlonnunes.carrental.model.Role;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.repository.RoleRepository;
import com.marlonnunes.carrental.utils.StringUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    private UserService userService;

    @Autowired
    private KeycloakAPI keycloakAPI;

    @Autowired EmailService emailService;

    @Autowired
    private RoleRepository roleRepository;



    public ResponseEntity<UserDTO> createUser(CreateUserDTO userDTO, User authUser){
        this.userService.checkIfUserIsAlreadyRegistered(userDTO);

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        user.setCreatedBy(authUser);
        user.setUpdatedBy(authUser);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        Role role = this.roleRepository.findById(userDTO.roleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "t"));
        List<IdNameDTO> rolesKeycloak = Arrays.asList(new IdNameDTO(role.getKeycloakId(), role.getName()));

        UserKeycloakDTO userKeycloak = this.keycloakAPI.createUser(SaveUserKeycloakDTO.fromCreateUserDto(userDTO), rolesKeycloak).getBody();
        user.setRole(role);
        user.setKeycloakId(userKeycloak.id());
        this.sendEmailToCreatePassword(user);

        return ResponseEntity.created(null).body(UserDTO.fromUser(user));
    }

    private void sendEmailToCreatePassword(User user){
        String verificationCode = StringUtils.verificationCodeGenerator();

        user.setVerificationCode(verificationCode);
        user.setVerificationCodeValidUntil(LocalDateTime.now().plusDays(2));

        user = this.userService.saveUser(user);

        this.emailService.sendEmailToCreatePassword(user);
    }

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "service.registration-service.reset-password.verification-code.invalid");
        }

        if(user.getVerificationCodeValidUntil().isBefore(LocalDateTime.now())){
            this.sendEmailToResetPassword(user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "service.registration-service.reset-password.verification-code.expired");
        }

        ResponseEntity<Void> result = this.keycloakAPI.resetPassword(user.getKeycloakId(), resetPassword.credentialId(), resetPassword.password());

        user.setVerificationCodeValidUntil(null);
        user.setVerificationCode(null);
        user.setUpdatedAt(LocalDateTime.now());

        return result;
    }

    public ResponseEntity<Void> createPassword(CreatePasswordDTO createPassword) {

        User user = this.userService.getById(createPassword.userId());

        if(!createPassword.verificationCode().equals(user.getVerificationCode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "service.registration-service.reset-password.verification-code.invalid");
        }

        if(user.getVerificationCodeValidUntil().isBefore(LocalDateTime.now())){
            this.sendEmailToResetPassword(user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "service.registration-service.reset-password.verification-code.expired");
        }

        ResponseEntity<Void> result = this.keycloakAPI.resetPassword(user.getKeycloakId(), null, createPassword.password());

        user.setVerificationCodeValidUntil(null);
        user.setVerificationCode(null);
        user.setUpdatedAt(LocalDateTime.now());

        return result;
    }

    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(this.roleRepository.findAll().stream().map(r -> new RoleDTO(r.getId(), r.getName(), r.getDescription())).toList());
    }
}
