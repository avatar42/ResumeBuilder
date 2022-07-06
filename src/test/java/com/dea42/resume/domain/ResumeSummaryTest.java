package com.dea42.resume.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dea42.resume.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumeSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeSummary.class);
        ResumeSummary resumeSummary1 = new ResumeSummary();
        resumeSummary1.setId(1L);
        ResumeSummary resumeSummary2 = new ResumeSummary();
        resumeSummary2.setId(resumeSummary1.getId());
        assertThat(resumeSummary1).isEqualTo(resumeSummary2);
        resumeSummary2.setId(2L);
        assertThat(resumeSummary1).isNotEqualTo(resumeSummary2);
        resumeSummary1.setId(null);
        assertThat(resumeSummary1).isNotEqualTo(resumeSummary2);
    }
}
