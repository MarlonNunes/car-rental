package com.marlonnunes.carrental.service;

import com.marlonnunes.carrental.dto.commons.PageDTO;
import com.marlonnunes.carrental.dto.user.CreateUserDTO;
import com.marlonnunes.carrental.dto.user.UserDTO;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.repository.UserRepository;
import com.marlonnunes.carrental.repository.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User getUserByEmail(String email){
        return this.repository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "service.user-service.default.not-found"));
    }

    public User getUserByKeycloakId(String keycloakId){
        return this.repository.findByKeycloakId(keycloakId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "service.user-service.default.not-found"));
    }

    public User getById(Long userId){
        return this.repository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "service.user-service.default.not-found"));
    }

    public List<User> getByIds(Collection<Long> ids){
        List<User> users = this.repository.findAllById(ids);
        if(users.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "service.user-service.get-by-ids.empty-list");
        }

        return users;
    }

    public void checkIfUserIsAlreadyRegistered(CreateUserDTO userDTO){
        List<User> users = this.repository.findByEmailOrCpf(userDTO.email(), userDTO.cpf());

        if(!users.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "service.user-service.default.already-exists");
        }
    }

    public User saveUser(User user){
        return this.repository.save(user);
    }

    public ResponseEntity<PageDTO<UserDTO>> search(List<String> names, List<String> emails, List<String> cpfs, List<Long> ids, LocalDate createdAtIni, LocalDate createdAtEnd, Integer page, Integer pageSize, User authUser) {
        Pageable pageable = PageRequest.of(page -1, pageSize, Sort.by("createdAt").ascending());

        Page<User> userPage = this.repository.findAll(UserSpecification.byCriteria(ids, names, emails, cpfs, createdAtIni, createdAtEnd, authUser), pageable);

        return ResponseEntity.ok(PageDTO.buildFromPage(userPage, UserDTO::fromUser));
    }
}
