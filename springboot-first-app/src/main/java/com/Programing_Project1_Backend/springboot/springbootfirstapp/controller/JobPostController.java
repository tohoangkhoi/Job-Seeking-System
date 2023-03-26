package com.Programing_Project1_Backend.springboot.springbootfirstapp.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Programing_Project1_Backend.springboot.springbootfirstapp.model.JobPost;

import com.Programing_Project1_Backend.springboot.springbootfirstapp.service.JobPostService;
import com.Programing_Project1_Backend.springboot.springbootfirstapp.service.UserService;

;

/*
 * this controller is responsible for executing all the requests 
 * made by an employer over the application
 */
@CrossOrigin(origins = "http://job-hunter.s3-website-ap-southeast-2.amazonaws.com")
@RestController
@RequestMapping("/api/job/")

public class JobPostController {

  @Autowired
  public JobPostService jobPostService;
  @Autowired
  UserService userService;

  /*
   * controller takes in the employer user name as parameter and
   * returns a list of all jobs created by an employer
   * stored in the database
   */

  @GetMapping("/user/alljobs/{employerId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYER', 'JOB_SEEKER')")
  public List<JobPost> getAllJobs(@PathVariable Long employerId) {
    return jobPostService.getAllJobsByEmployerId(employerId);
  }

  @GetMapping("/{post_id}")
  public JobPost getJobPostById(@PathVariable Long post_id) {
    return jobPostService.getJobPostById(post_id);
  }

  @GetMapping("/user/match")
  // @PreAuthorize("hasRole('JOB_SEEKER')")
  public Iterable<JobPost> findAllJobPostsByUserBackground(Principal principal) {
    return jobPostService.getJobPostByUserBackground(principal.getName());
  }

  @GetMapping("/alljobs")
  public Iterable<JobPost> findAllJobPosts() {
    return jobPostService.getAllJobPost();
  }

  @PostMapping("/employer/postjob/{employer_id}")
  @PreAuthorize("hasRole('EMPLOYER')")
  public void saveJob(@RequestBody JobPost info, @PathVariable Long employer_id) {
    jobPostService.saveJob(info, employer_id);
  }
}
