package com.example.medcin_app.security;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class securityController {

    @GetMapping("/notAuthorized")
    public  String notAuthorized(){
        return "404";
    }
}
