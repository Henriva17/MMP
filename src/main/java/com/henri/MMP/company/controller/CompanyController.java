package com.henri.MMP.company.controller;

import com.henri.MMP.company.dto.CompanyResponse;
import com.henri.MMP.company.dto.CreateCompanyProfileRequest;
import com.henri.MMP.company.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
<<<<<<< HEAD

=======
@CrossOrigin(origins = "http://localhost:4200")
>>>>>>> henridev
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<CompanyResponse> createProfile(
            @PathVariable Long userId,
            @Valid @RequestBody CreateCompanyProfileRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.createProfile(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getById(id));
    }

    @GetMapping("/verified")
    public ResponseEntity<List<CompanyResponse>> getVerifiedCompanies() {
        return ResponseEntity.ok(companyService.getVerifiedCompanies());
    }

    @GetMapping("/domain/{domain}")
    public ResponseEntity<List<CompanyResponse>> getByDomain(@PathVariable String domain) {
        return ResponseEntity.ok(companyService.getByDomain(domain));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<CompanyResponse> verify(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.verifyCompany(id));
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<CompanyResponse> updateDescription(@PathVariable Long id, @RequestParam String description) {
        return ResponseEntity.ok(companyService.updateDescription(id, description));
    }

    @PutMapping("/{id}/website")
    public ResponseEntity<CompanyResponse> updateWebsiteLink(@PathVariable Long id, @RequestParam String websiteLink) {
        return ResponseEntity.ok(companyService.updateWebsiteLink(id, websiteLink));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        companyService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
