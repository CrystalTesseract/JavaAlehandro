package org.alexander.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class HealthCheckController {
    @GetMapping("/")
    public String handleRequest() {
        return "Z";
    }

}
