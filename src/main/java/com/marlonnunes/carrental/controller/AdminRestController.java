package com.marlonnunes.carrental.controller;

import com.marlonnunes.carrental.dto.commons.IdNameDTO;
import com.marlonnunes.carrental.dto.user.CreateUserDTO;
import com.marlonnunes.carrental.dto.user.UserDTO;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {


    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO userDTO, @AuthenticationPrincipal User user){
        return this.registrationService.createUser(userDTO, user);
    }

    @GetMapping("/getRoles")
    public ResponseEntity<List<IdNameDTO>> getRoles(){
        return this.registrationService.getAllRoles();
    }


}
