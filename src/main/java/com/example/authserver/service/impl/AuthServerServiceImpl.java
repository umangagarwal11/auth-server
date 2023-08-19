package com.example.authserver.service.impl;

import com.example.authserver.entity.User;
import com.example.authserver.exception.TechnicalException;
import com.example.authserver.repository.UserRepository;
import com.example.authserver.service.AuthServerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class AuthServerServiceImpl implements AuthServerService {

    @Autowired
    private UserRepository userRepository;

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
            return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            throw new TechnicalException(e.getMessage());
        }
    }
}
