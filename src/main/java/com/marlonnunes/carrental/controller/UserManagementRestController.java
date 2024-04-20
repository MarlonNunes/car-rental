package com.marlonnunes.carrental.controller;

import com.marlonnunes.carrental.dto.commons.PageDTO;
import com.marlonnunes.carrental.dto.keycloak.RoleDTO;
import com.marlonnunes.carrental.dto.user.CreateUserDTO;
import com.marlonnunes.carrental.dto.user.UserDTO;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.service.RegistrationService;
import com.marlonnunes.carrental.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user-management")
public class UserManagementRestController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO userDTO, @AuthenticationPrincipal User user){
        return this.registrationService.createUser(userDTO, user);
    }

    @GetMapping("/search")
    public ResponseEntity<PageDTO<UserDTO>> getUsers(
            @RequestParam(name = "name", required = false) List<String> names,
            @RequestParam(name = "email", required = false) List<String> emails,
            @RequestParam(name = "cpf", required = false) List<String> cpfs,
            @RequestParam(name = "id", required = false) List<Long> ids,
            @RequestParam(name = "createdAtIni", required = false) LocalDate createdAtIni,
            @RequestParam(name = "createdAtEnd", required = false) LocalDate createdAtEnd,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @AuthenticationPrincipal User authUser
    ){
        return this.userService.search(names, emails, cpfs, ids, createdAtIni, createdAtEnd, page, pageSize, authUser);
    }


    @GetMapping("/getRoles")
    public ResponseEntity<List<RoleDTO>> getRoles(){
        return this.registrationService.getAllRoles();
    }
}
