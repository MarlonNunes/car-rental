package com.marlonnunes.carrental.service;

import com.marlonnunes.carrental.dto.user.CreateUserDTO;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User getUserByEmail(String email){
        return this.repository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public User getUserByKeycloakId(String keycloakId){
        return this.repository.findByKeycloakId(keycloakId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public User getById(Long userId){
        return this.repository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public void checkIfUserIsAlreadyRegistered(CreateUserDTO userDTO){
        List<User> users = this.repository.findByEmailOrCpf(userDTO.email(), userDTO.cpf());

        if(!users.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado");
        }
    }

    public User saveUser(User user){
        return this.repository.save(user);
    }
}
