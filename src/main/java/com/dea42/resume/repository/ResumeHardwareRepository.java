package com.dea42.resume.repository;

import com.dea42.resume.domain.ResumeHardware;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResumeHardware entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumeHardwareRepository extends JpaRepository<ResumeHardware, Long> {}
