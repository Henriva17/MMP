package com.henri.MMP.jobposting.service;

import com.henri.MMP.Enums.JobPostingStatus;
import com.henri.MMP.company.model.Company;
import com.henri.MMP.company.repository.CompanyRepository;
import com.henri.MMP.jobposting.dto.CreateJobPostingRequest;
import com.henri.MMP.jobposting.dto.JobPostingResponse;
import com.henri.MMP.jobposting.mapper.JobPostingMapper;
import com.henri.MMP.jobposting.model.JobPosting;
import com.henri.MMP.jobposting.repository.JobPostingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;

    public JobPostingService(JobPostingRepository jobPostingRepository, CompanyRepository companyRepository) {
        this.jobPostingRepository = jobPostingRepository;
        this.companyRepository = companyRepository;
    }

    public JobPostingResponse createPosting(Long companyId, CreateJobPostingRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found: " + companyId));

        JobPosting posting = new JobPosting(
                company,
                request.jobTitle(),
                request.description(),
                request.jobType(),
                request.workMode(),
                request.location(),
                request.requiredEducationLevel(),
                request.applicationDeadline()
        );

        posting.updateRequiredSkills(request.requiredSkills());

        return JobPostingMapper.toResponse(jobPostingRepository.save(posting));
    }

    public JobPostingResponse publish(Long id) {
        JobPosting posting = findPostingEntityById(id);
        posting.publish();
        return JobPostingMapper.toResponse(jobPostingRepository.save(posting));
    }

    public JobPostingResponse close(Long id) {
        JobPosting posting = findPostingEntityById(id);
        posting.close();
        return JobPostingMapper.toResponse(jobPostingRepository.save(posting));
    }

    public JobPostingResponse archive(Long id) {
        JobPosting posting = findPostingEntityById(id);
        posting.archive();
        return JobPostingMapper.toResponse(jobPostingRepository.save(posting));
    }

    public void delete(Long id) {
        JobPosting posting = findPostingEntityById(id);
        if (posting.getJobPostingStatus() != JobPostingStatus.DRAFT) {
            throw new IllegalStateException("Only draft job postings can be deleted.");
        }
        jobPostingRepository.delete(posting);
    }

    @Transactional(readOnly = true)
    public List<JobPostingResponse> getAllPostings() {
        return jobPostingRepository.findAll()
                .stream()
                .map(JobPostingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<JobPostingResponse> getOpenPostings() {
        return jobPostingRepository.findByJobPostingStatusAndApplicationDeadlineAfter(
                        JobPostingStatus.OPEN,
                        LocalDate.now()
                ).stream()
                .map(JobPostingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<JobPostingResponse> getByCompany(Long companyId) {
        return jobPostingRepository.findByCompany_Id(companyId)
                .stream()
                .map(JobPostingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public JobPostingResponse getById(Long id) {
        return JobPostingMapper.toResponse(findPostingEntityById(id));
    }

    protected JobPosting findPostingEntityById(Long id) {
        return jobPostingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job posting not found: " + id));
    }
}
