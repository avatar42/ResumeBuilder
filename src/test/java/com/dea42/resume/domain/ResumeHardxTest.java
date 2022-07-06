package com.dea42.resume.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dea42.resume.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumeHardxTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeHardx.class);
        ResumeHardx resumeHardx1 = new ResumeHardx();
        resumeHardx1.setId(1L);
        ResumeHardx resumeHardx2 = new ResumeHardx();
        resumeHardx2.setId(resumeHardx1.getId());
        assertThat(resumeHardx1).isEqualTo(resumeHardx2);
        resumeHardx2.setId(2L);
        assertThat(resumeHardx1).isNotEqualTo(resumeHardx2);
        resumeHardx1.setId(null);
        assertThat(resumeHardx1).isNotEqualTo(resumeHardx2);
    }
}
