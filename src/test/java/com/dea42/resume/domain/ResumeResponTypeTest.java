package com.dea42.resume.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dea42.resume.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumeResponTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeResponType.class);
        ResumeResponType resumeResponType1 = new ResumeResponType();
        resumeResponType1.setId(1L);
        ResumeResponType resumeResponType2 = new ResumeResponType();
        resumeResponType2.setId(resumeResponType1.getId());
        assertThat(resumeResponType1).isEqualTo(resumeResponType2);
        resumeResponType2.setId(2L);
        assertThat(resumeResponType1).isNotEqualTo(resumeResponType2);
        resumeResponType1.setId(null);
        assertThat(resumeResponType1).isNotEqualTo(resumeResponType2);
    }
}
