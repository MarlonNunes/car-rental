package com.marlonnunes.carrental.repository;

import com.marlonnunes.carrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findByKeycloakId(String keycloakId);

    List<User> findByEmailOrCpf(String email, String cpf);
}
