package org.alexander.project.controllers;

import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class RequestController {
    @GetMapping("/")
    public String handleRequest() {
        return "Z";
    }

}
