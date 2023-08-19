package com.example.authserver.controller;

import com.example.authserver.entity.User;
import com.example.authserver.service.AuthServerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Log4j2
public class AdminController {

    @Autowired
    private AuthServerService authServerService;

    @PostMapping("/addUser")
    @PreAuthorize("authentication.principal.username == 'admin'")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            return authServerService.addUser(user);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
