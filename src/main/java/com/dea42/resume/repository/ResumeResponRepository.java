package com.dea42.resume.repository;

import com.dea42.resume.domain.ResumeRespon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResumeRespon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeResponRepository extends JpaRepository<ResumeRespon, Long> {}
