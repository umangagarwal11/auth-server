package com.example.authserver.service.impl;

import com.example.authserver.service.AuthServerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class AuthServerServiceImpl implements AuthServerService {

    @Override
    public String hello(String msg){
        log.info("Service class");
        return msg+" from service class";
    }

}
