package com.marlonnunes.carrental.security;

import com.marlonnunes.carrental.service.UserService;
import com.marlonnunes.carrental.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Map;

public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserService userService;

    public JwtConverter(UserService userService){
        this.userService = userService;
    }
    @Override
    public AbstractAuthenticationToken convert(Jwt source) {

        String sub = source.getClaim("sub");

        User user = this.userService.getUserByKeycloakId(sub);


        Map<String, Collection<String>> realm = source.getClaim("realm_access");

        Collection<String> roles = realm.get("roles");

        var authorities = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}
