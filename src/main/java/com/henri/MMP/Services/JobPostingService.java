package com.henri.MMP.Services;

import com.henri.MMP.Enums.JobPostingStatus;
import com.henri.MMP.Models.Company;
import com.henri.MMP.Models.JobPosting;
import com.henri.MMP.Repositories.CompanyRepository;
import com.henri.MMP.Repositories.JobPostingRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;

    public JobPostingService(JobPostingRepository jobPostingRepository,
                             CompanyRepository companyRepository) {
        this.jobPostingRepository = jobPostingRepository;
        this.companyRepository = companyRepository;
    }

    public JobPosting createPosting(Long companyId, JobPosting request) {
        Company company = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + companyId));

        JobPosting posting = new JobPosting(
                company,
                request.getJobTitle(),
                request.getDescription(),
                request.getJobType(),
                request.getWorkMode(),
                request.getLocation(),
                request.getRequiredEducationLevel(),
                request.getApplicationDeadline()
        );

        posting.updateRequiredSkills(request.getRequiredSkills());

        return jobPostingRepository.save(posting);
    }

    public JobPosting publish(Long id) {
        JobPosting posting = getById(id);
        posting.publish();
        return jobPostingRepository.save(posting);
    }

    public JobPosting close(Long id) {
        JobPosting posting = getById(id);
        posting.close();
        return jobPostingRepository.save(posting);
    }

    public JobPosting archive(Long id) {
        JobPosting posting = getById(id);
        posting.archive();
        return jobPostingRepository.save(posting);
    }

    @Transactional(readOnly = true)
    public List<JobPosting> getAllPostings() {
        return jobPostingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<JobPosting> getOpenPostings() {
        return jobPostingRepository.findByJobPostingStatusAndApplicationDeadlineAfter(
                JobPostingStatus.OPEN,
                java.time.LocalDate.now()
        );
    }

    @Transactional(readOnly = true)
    public List<JobPosting> getByCompany(Long companyId) {
        return jobPostingRepository.findByCompany_Id(companyId);
    }

    @Transactional(readOnly = true)
    public JobPosting getById(Long id) {
        return jobPostingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job posting not found: " + id));
    }
}
