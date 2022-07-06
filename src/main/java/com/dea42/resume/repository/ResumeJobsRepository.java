package com.dea42.resume.repository;

import com.dea42.resume.domain.ResumeJobs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResumeJobs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeJobsRepository extends JpaRepository<ResumeJobs, Long> {}
