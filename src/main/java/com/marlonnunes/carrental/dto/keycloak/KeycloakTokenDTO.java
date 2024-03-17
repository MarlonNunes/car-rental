package com.marlonnunes.carrental.dto.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class KeycloakTokenDTO {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    public LocalDateTime getExpireDate(){
        if(Objects.isNull(this.expiresIn)) return null;

        return LocalDateTime.now().plusSeconds(expiresIn);
    }
}
