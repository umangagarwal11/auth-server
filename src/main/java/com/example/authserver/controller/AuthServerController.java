package com.example.authserver.controller;

import com.example.authserver.service.AuthServerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@Log4j2
@CrossOrigin("*")
public class AuthServerController {

    @Autowired
    AuthServerService authServerService;

    @GetMapping("/hello")
    public String hello(@RequestParam("msg") String msg){
        log.info(msg);
        return authServerService.hello(msg);
    }

}
