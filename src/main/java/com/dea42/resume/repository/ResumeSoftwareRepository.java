package com.dea42.resume.repository;

import com.dea42.resume.domain.ResumeSoftware;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResumeSoftware entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeSoftwareRepository extends JpaRepository<ResumeSoftware, Long> {}
