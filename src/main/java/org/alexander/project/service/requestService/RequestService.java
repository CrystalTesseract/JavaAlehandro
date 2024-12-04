package org.alexander.project.service.requestService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestService {
    @GetMapping("/")
    public String handleRequest() {
        return "Z";
    }
}
