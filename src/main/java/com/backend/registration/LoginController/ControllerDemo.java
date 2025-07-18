package com.backend.registration.LoginController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerDemo {

    @GetMapping("/Hello")
    public String getName(){
        return "Amarjeet Singh Rajput";
    }
}
