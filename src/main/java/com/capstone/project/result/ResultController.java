package com.capstone.project.result;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/result")
    public List<Result> getResult(Principal principal){
        return resultService.getResult(principal.getName());
    }
}
