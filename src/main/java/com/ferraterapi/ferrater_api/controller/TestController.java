package com.ferraterapi.ferrater_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/home")
public class TestController {
    @GetMapping
    public String home() {
        return "Hello World! desde test controller";
    }

    
    
}
