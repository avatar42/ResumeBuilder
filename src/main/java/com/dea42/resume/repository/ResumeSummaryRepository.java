package com.dea42.resume.repository;

import com.dea42.resume.domain.ResumeSummary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResumeSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeSummaryRepository extends JpaRepository<ResumeSummary, Long> {}
