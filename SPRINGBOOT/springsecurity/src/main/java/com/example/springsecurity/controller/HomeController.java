package com.example.springsecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    @RequestMapping ("/")
    public String getHome(HttpServletRequest request){
        return "Welcome Aliens " + request.getSession().getId() + " Id";
    }

    //csrf token
}
