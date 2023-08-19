package com.example.authserver.service;

import com.example.authserver.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthServerService {
    String hello(String msg);

    ResponseEntity<Object> addUser(User user);
}
