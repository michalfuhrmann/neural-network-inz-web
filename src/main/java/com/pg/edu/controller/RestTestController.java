package com.pg.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTestController {


    @RequestMapping(value = "/rests/test", method = RequestMethod.GET)
    public ResponseEntity<String> test() {

        return ResponseEntity.ok("pls");
    }
}
