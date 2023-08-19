package com.example.authserver.service.impl;

import com.example.authserver.entity.User;
import com.example.authserver.exception.TechnicalException;
import com.example.authserver.repository.UserRepository;
import com.example.authserver.service.AuthServerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Log4j2
@Component
public class AuthServerServiceImpl implements AuthServerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public String hello(String msg){
        log.info("Service class");
        return msg+" from service class";
    }

    @Override
    public ResponseEntity<Object> addUser(User user) {
        try {
            String pwd = user.getPassword();
            String encryptedPwd = new BCryptPasswordEncoder().encode(pwd);
            user.setPassword(encryptedPwd);
            log.info("Saving user to DB {}", user);
            userRepository.save(user);

            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId(user.getUsername())
                    .clientSecret(user.getPassword())
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .redirectUri("http://127.0.0.1:8242/login/oauth2/code/users-client-oidc")
                    .redirectUri("http://127.0.0.1:8242/authorized")
                    .scope(OidcScopes.OPENID)
                    .scope("read")
                    //.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                    .build();

            new JdbcRegisteredClientRepository(jdbcTemplate).save(registeredClient);

            return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            throw new TechnicalException(e.getMessage());
        }
    }
}
