package com.dea42.resume.repository;

import com.dea42.resume.domain.ResumeResponType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResumeResponType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeResponTypeRepository extends JpaRepository<ResumeResponType, Long> {}
