package com.dea42.resume.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dea42.resume.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumeJobsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeJobs.class);
        ResumeJobs resumeJobs1 = new ResumeJobs();
        resumeJobs1.setId(1L);
        ResumeJobs resumeJobs2 = new ResumeJobs();
        resumeJobs2.setId(resumeJobs1.getId());
        assertThat(resumeJobs1).isEqualTo(resumeJobs2);
        resumeJobs2.setId(2L);
        assertThat(resumeJobs1).isNotEqualTo(resumeJobs2);
        resumeJobs1.setId(null);
        assertThat(resumeJobs1).isNotEqualTo(resumeJobs2);
    }
}
