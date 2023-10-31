package com.mirea.diplom.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class RestExamle {

    @PostMapping("/hello")
    public void postMappingExample(@RequestBody String request) {
        System.out.println(request);
    }
}
