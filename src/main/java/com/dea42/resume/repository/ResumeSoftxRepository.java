package com.dea42.resume.repository;

import com.dea42.resume.domain.ResumeSoftx;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResumeSoftx entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeSoftxRepository extends JpaRepository<ResumeSoftx, Long> {}
