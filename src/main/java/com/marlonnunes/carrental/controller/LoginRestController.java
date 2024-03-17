package com.marlonnunes.carrental.controller;

import com.marlonnunes.carrental.dto.registration.CreatePasswordDTO;
import com.marlonnunes.carrental.dto.registration.ResetPasswordDTO;
import com.marlonnunes.carrental.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginRestController {


    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/sendEmailToResetPassword")
    public ResponseEntity<Void> sendEmailToResetPassword(@RequestParam String email){
        return this.registrationService.sendEmailToResetPassword(email);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPassword){
        return this.registrationService.resetPassword(resetPassword);
    }

    @PostMapping("/createPassword")
    public ResponseEntity<Void> createPassword(@Valid @RequestBody CreatePasswordDTO createPassword){
        return this.registrationService.createPassword(createPassword);
    }
}
