package com.marlonnunes.carrental.jobs;

import com.marlonnunes.carrental.dto.keycloak.KeycloakRoleDTO;
import com.marlonnunes.carrental.keycloak.KeycloakAPI;
import com.marlonnunes.carrental.model.Role;
import com.marlonnunes.carrental.repository.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log4j2
public class UpdateRoleFromKeycloak {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private KeycloakAPI keycloakAPI;
    @Scheduled(cron = "0 * * * * ?")
    public void execute(){

        List<KeycloakRoleDTO> keycloakRoles = keycloakAPI.getAllRoles().getBody().stream().filter(k -> !k.composite()).toList();
        List<Role> systemRoles = this.repository.findAll();

        if(keycloakRoles.size() != systemRoles.size()){
            Set<String> savedKeycloakIds = systemRoles.stream().map(Role::getKeycloakId).collect(Collectors.toSet());

            Set<Role>  rolesToSave = keycloakRoles.stream()
                    .filter(kr -> !savedKeycloakIds.contains(kr.id()) && !kr.composite())
                    .map(kr -> {
                        Role role = new Role();
                        role.setKeycloakId(kr.id());
                        role.setName(kr.name());
                        role.setDescription(kr.description());

                        return role;
                    }).collect(Collectors.toSet());


            this.repository.saveAll(rolesToSave);

            log.info("updated roles from keycloak");
        }

    }
}
