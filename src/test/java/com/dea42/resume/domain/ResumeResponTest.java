package com.dea42.resume.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dea42.resume.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumeResponTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeRespon.class);
        ResumeRespon resumeRespon1 = new ResumeRespon();
        resumeRespon1.setId(1L);
        ResumeRespon resumeRespon2 = new ResumeRespon();
        resumeRespon2.setId(resumeRespon1.getId());
        assertThat(resumeRespon1).isEqualTo(resumeRespon2);
        resumeRespon2.setId(2L);
        assertThat(resumeRespon1).isNotEqualTo(resumeRespon2);
        resumeRespon1.setId(null);
        assertThat(resumeRespon1).isNotEqualTo(resumeRespon2);
    }
}
