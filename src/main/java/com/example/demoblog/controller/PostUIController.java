package com.example.demoblog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class PostUIController {

    @GetMapping(value = "/dashboard")
    public String showPostGrid(){


        return "index";
    }
}
