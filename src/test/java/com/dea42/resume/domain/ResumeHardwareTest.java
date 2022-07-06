package com.dea42.resume.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dea42.resume.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumeHardwareTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeHardware.class);
        ResumeHardware resumeHardware1 = new ResumeHardware();
        resumeHardware1.setId(1L);
        ResumeHardware resumeHardware2 = new ResumeHardware();
        resumeHardware2.setId(resumeHardware1.getId());
        assertThat(resumeHardware1).isEqualTo(resumeHardware2);
        resumeHardware2.setId(2L);
        assertThat(resumeHardware1).isNotEqualTo(resumeHardware2);
        resumeHardware1.setId(null);
        assertThat(resumeHardware1).isNotEqualTo(resumeHardware2);
    }
}
