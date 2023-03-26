package com.Programing_Project1_Backend.springboot.springbootfirstapp.controller;

import com.Programing_Project1_Backend.springboot.springbootfirstapp.model.Application;
import com.Programing_Project1_Backend.springboot.springbootfirstapp.model.User;
import com.Programing_Project1_Backend.springboot.springbootfirstapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://job-hunter.s3-website-ap-southeast-2.amazonaws.com")
@RestController
@RequestMapping("/api/application")
public class ApplicationController {
    @Autowired
    public MapValidationErrorService mapValidationErrorService;
    @Autowired
    public ApplicationService applicationService;
    @Autowired
    public JobPostService jobPostService;
    @Autowired
    public UserService userService;


    @GetMapping("/post/all/{postId}")
    public List<Application> getApplicationByPostId(@PathVariable Long postId) {
        return applicationService.getApplicationsPostId(postId);
    }

    @GetMapping("/user/all/{candidateId}")
    public List<Application> getApplicationByCandteId(@PathVariable Long candidateId) {
        return applicationService.getApplicationsPostId(candidateId);
    }

    @GetMapping("/user/{applicationId}")
    public Application getApplicationById(@PathVariable Long applicationId) {
        return applicationService.getApplicationById(applicationId);
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<?> saveJob(@Valid @RequestBody Application application, BindingResult result, @PathVariable Long postId, Principal principal) {
        ResponseEntity<?>errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap !=null) return errorMap;

         User candidate = applicationService.saveApplication(application, postId, principal.getName()).getCandidate();
         return new ResponseEntity<User>(candidate, HttpStatus.CREATED);
    }
}
