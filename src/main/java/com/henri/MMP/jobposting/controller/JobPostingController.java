package com.henri.MMP.jobposting.controller;

import com.henri.MMP.jobposting.dto.CreateJobPostingRequest;
import com.henri.MMP.jobposting.dto.JobPostingResponse;
import com.henri.MMP.jobposting.service.JobPostingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/job-postings")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @PostMapping("/companies/{companyId}")
    public ResponseEntity<JobPostingResponse> createPosting(
            @PathVariable Long companyId,
            @Valid @RequestBody CreateJobPostingRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobPostingService.createPosting(companyId,request));
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<JobPostingResponse> publish(@PathVariable Long id) {
        return ResponseEntity.ok(jobPostingService.publish(id));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<JobPostingResponse> close(@PathVariable Long id) {
        return ResponseEntity.ok(jobPostingService.close(id));
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<JobPostingResponse> archive(@PathVariable Long id) {
        return ResponseEntity.ok(jobPostingService.archive(id));
    }

    @GetMapping
    public ResponseEntity<List<JobPostingResponse>> getAllPostings() {
        return ResponseEntity.ok(jobPostingService.getAllPostings());
    }

    @GetMapping("/open")
    public ResponseEntity<List<JobPostingResponse>> getOpenPostings() {
        return ResponseEntity.ok(jobPostingService.getOpenPostings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPostingResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jobPostingService.getById(id));
    }

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<List<JobPostingResponse>> getByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(jobPostingService.getByCompany(companyId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobPostingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
