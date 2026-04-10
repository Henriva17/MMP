package com.henri.MMP.jobapplication.controller;

import com.henri.MMP.jobapplication.dto.ApplyToJobRequest;
import com.henri.MMP.jobapplication.dto.JobApplicationResponse;
import com.henri.MMP.jobapplication.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping("/students/{studentId}")
    public ResponseEntity<JobApplicationResponse> apply(
            @PathVariable Long studentId,
            @Valid @RequestBody ApplyToJobRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobApplicationService.apply(studentId, request));
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<JobApplicationResponse> review(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.review(id));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<JobApplicationResponse> accept(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.accept(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<JobApplicationResponse> reject(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.reject(id));
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<JobApplicationResponse> withdraw(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.withdraw(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.getById(id));
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<JobApplicationResponse>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(jobApplicationService.getByStudent(studentId));
    }

    @GetMapping("/job-postings/{jobPostingId}")
    public ResponseEntity<List<JobApplicationResponse>> getByJobPosting(@PathVariable Long jobPostingId) {
        return ResponseEntity.ok(jobApplicationService.getByJobPosting(jobPostingId));
    }
}
